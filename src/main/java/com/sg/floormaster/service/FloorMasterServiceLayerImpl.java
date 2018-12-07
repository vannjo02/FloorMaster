package com.sg.floormaster.service;

/**
 *
 * @author Joshua Vannatter
 */
import com.sg.floormaster.dao.FloorMasterPersistenceException;
import com.sg.floormaster.dto.Order;
import java.util.List;
import java.util.Set;
import com.sg.floormaster.dao.FloorMasterConfigDao;
import com.sg.floormaster.dao.FloorMasterDao;
import com.sg.floormaster.dao.FloorMasterProductsDao;
import com.sg.floormaster.dao.FloorMasterStatesDao;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class FloorMasterServiceLayerImpl implements FloorMasterServiceLayer {

    private final FloorMasterDao dao;
    private final FloorMasterStatesDao statesDao;
    private final FloorMasterProductsDao productsDao;
    private final FloorMasterConfigDao configDao;
    private final DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("MMddyyyy");
    private boolean isTraining;

    public FloorMasterServiceLayerImpl(FloorMasterDao dao, FloorMasterStatesDao statesDao,
            FloorMasterProductsDao productsDao, FloorMasterConfigDao configDao) {
        this.dao = dao;
        this.statesDao = statesDao;
        this.productsDao = productsDao;
        this.configDao = configDao;
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate date) throws FloorMasterPersistenceException {
        List<Order> list = dao.getOrders().stream().filter(o -> o.getDate().equals(date)).collect(Collectors.toList());
        return list;
    }

    @Override
    public Order getOrder(int id) throws FloorMasterPersistenceException {
        if (dao.getOrder(id) == null) {
            throw new FloorMasterPersistenceException("That ID doesn't exist in the database.");
        }
        Order order = dao.getOrder(id);
        return order;
    }

    @Override
    public Set<Integer> getIdsFromDate(LocalDate date) throws FloorMasterPersistenceException {
        return dao.getIds().stream().filter((i -> dao.getOrder(i).getDate().equals(date))).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getStates() {
        return statesDao.getStates();
    }

    @Override
    public Set<String> getProds() {
        return productsDao.getProds();
    }

    @Override
    public Order addOrder(Map<String, String> orderVals) throws FloorMasterPersistenceException, FloorMasterInvalidStateException,
            FloorMasterInvalidProductException, FloorMasterInvalidNumberException {

        validateState(orderVals.get("state"));
        validateProduct(orderVals.get("product"));
        validateArea(orderVals.get("area"));

        Order newOrder = new Order();
        newOrder.setCustomerName(orderVals.get("name"));
        newOrder.setState(orderVals.get("state"));
        newOrder.setDate(LocalDate.now());
        newOrder.setArea(new BigDecimal(orderVals.get("area")));
        newOrder.setProductType(orderVals.get("product"));

        addDependencies(newOrder);
        dao.addOrder(newOrder);
        dao.writeFile(isTraining, "compact");
        return newOrder;
    }

    @Override
    public Order editOrder(Order order, Map<String, String> newVals) throws FloorMasterPersistenceException, FloorMasterInvalidStateException,
            FloorMasterInvalidProductException, FloorMasterInvalidNumberException {

        if (newVals.get("state").isEmpty()) {
            newVals.put("state", order.getState());
        } else {
            validateState(newVals.get("state"));
        }
        if (newVals.get("product").isEmpty()) {
            newVals.put("product", order.getProductType());
        } else {
            validateProduct(newVals.get("product"));
        }
        if (newVals.get("area").isEmpty()) {
            newVals.put("area", order.getArea().toString());
        } else {
            validateArea(newVals.get("area"));
        }
        if (newVals.get("name").isEmpty()) {
            newVals.put("name", order.getCustomerName());
        }

        order.setState(newVals.get("state"));
        order.setProductType(newVals.get("product"));
        order.setArea(new BigDecimal(newVals.get("area")));
        order.setCustomerName(newVals.get("name"));
        
        addDependencies(order);
        dao.editOrder(order);
        dao.writeFile(isTraining, "compact");
        return order;
    }

    @Override
    public Order removeOrder(Order order) throws FloorMasterPersistenceException {
        dao.removeOrder(order);
        dao.writeFile(isTraining, "compact");
        return order;
    }

    @Override
    public void initStates() throws FloorMasterPersistenceException {
        statesDao.loadStatesAndTaxes();
    }

    @Override
    public void initProducts() throws FloorMasterPersistenceException {
        productsDao.loadProducts();
    }

    @Override
    public void initConfig() throws FloorMasterPersistenceException {
        configDao.loadConfigFile();
        this.isTraining = configDao.getIsTraining();
    }

    @Override
    public void initDatabase() throws FloorMasterPersistenceException, FileNotFoundException {
        dao.loadFile();
        List<Order> list = dao.getOrders();
        list.forEach(o -> addDependencies(o));
    }

    @Override
    public boolean isTraining() {
        return this.isTraining;
    }

    @Override
    public void saveDataToFiles() throws FloorMasterPersistenceException {
        Map<LocalDate, List<Order>> grouped = dao.getOrders().stream().collect(Collectors.groupingBy(Order::getDate));
        grouped.keySet().forEach((LocalDate date) -> {
            Map<Integer, Order> map = grouped.get(date).stream().collect(Collectors.toMap(Order::getOrderId, o -> o));
            try {
                dao.writeFile(map, createFileName(date), isTraining, "complex");
            } catch (FloorMasterPersistenceException e) {
                throw new RuntimeException("Unable to write the orders associated with date " + date + " to a file.");
            }
        });
    }

    private String createFileName(LocalDate date) {
        return "Orders_" + parseDate(date) + ".txt";
    }

    private String parseDate(LocalDate date) {
        return date.format(fileNameFormat);
    }

    private boolean validateArea(String str) throws FloorMasterInvalidNumberException {
        try {
            double d = Double.parseDouble(str);
            if (d < 0) {
                throw new FloorMasterInvalidNumberException("The area can't be negative");
            }
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new FloorMasterInvalidNumberException("The area you entered was not numeric.");
        }

        return true;
    }

    private void validateState(String state) throws FloorMasterInvalidStateException {
        if (!statesDao.getStates().contains(state)) {
            throw new FloorMasterInvalidStateException("There's no 'Flooring Master' locations in that state!");
        }
    }

    private void validateProduct(String product) throws FloorMasterInvalidProductException {
        if (!productsDao.getProds().contains(product)) {
            throw new FloorMasterInvalidProductException("We don't carry that product!");
        }
    }

    private void addDependencies(Order order) {
        BigDecimal taxRate = statesDao.getTaxRate(order.getState());
        BigDecimal costPerSqFt = productsDao.getCostPerSqFt(order.getProductType());
        BigDecimal laborCostPerSqFt = productsDao.getLaborCostPerSqFt(order.getProductType());
        
        order.setTaxRate(taxRate);
        order.setMaterialCostPerSqFt(costPerSqFt);
        order.setLaborCostPerSqFt(laborCostPerSqFt);
        order.setDependencies();
    }
}
