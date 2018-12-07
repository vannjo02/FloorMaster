package com.sg.floormaster.controller;

import com.sg.floormaster.dao.FloorMasterPersistenceException;
import com.sg.floormaster.dto.Order;
import com.sg.floormaster.service.FloorMasterInvalidNumberException;
import com.sg.floormaster.service.FloorMasterInvalidProductException;
import com.sg.floormaster.service.FloorMasterInvalidStateException;
import com.sg.floormaster.ui.FloorMasterView;
import com.sg.floormaster.service.FloorMasterServiceLayer;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterController {

    private final FloorMasterView view;
    private final FloorMasterServiceLayer service;

    public FloorMasterController(FloorMasterView view, FloorMasterServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        initialize();
        boolean keepGoing = true;
        int menuSelection;

        while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
        }
        exitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() {
        view.displayOrdersBanner();
        LocalDate date = view.getDateSelection();
        if (date != null) {
            try {
                view.displayOrderList(service.getOrdersFromDate(date));
            } catch (FloorMasterPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } else {
            view.displayActionCancelledBanner();
        }
    }

    private void addOrder() {
        view.displayAddOrderBanner();
        Map<String, String> orderVals = view.getNewOrderVals(service.getStates(), service.getProds());
        if (orderVals != null) {
            try {
                service.addOrder(orderVals);
                view.displayAddOrderSuccessBanner();
            } catch (FloorMasterPersistenceException | FloorMasterInvalidStateException
                    | FloorMasterInvalidProductException | FloorMasterInvalidNumberException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } else {
            view.displayActionCancelledBanner();
        }

    }

    private void editOrder() {
        view.displayEditOrderBanner();
        Set<String> states = service.getStates();
        Set<String> products = service.getProds();
        int id = view.getIdQuery();
        if (id != -1) {
            try {
                Order order = service.getOrder(id);
                Map<String, String> newVals = view.getEditOrderVals(order, states, products);
                service.editOrder(order, newVals);
                view.displayEditOrderSuccessBanner();
            } catch (FloorMasterPersistenceException | FloorMasterInvalidStateException
                    | FloorMasterInvalidProductException | FloorMasterInvalidNumberException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } else {
            view.displayActionCancelledBanner();
        }
    }

    private void removeOrder() {
        view.displayRemoveOrderBanner();
        int id = view.getIdQuery();
        if (id != -1) {
            try {
                Order order = service.getOrder(id);
                view.displayOrderInfo(order);
                if (view.getRemoveConfirm()) {
                    service.removeOrder(order);
                    view.displayRemoveOrderSuccessBanner();
                } else {
                    view.displayActionCancelledBanner();
                }
            } catch (FloorMasterPersistenceException e){
                view.displayErrorMessage(e.getMessage());
            }

        } else {
            view.displayActionCancelledBanner();
        }
    }

    private void saveWork() {
        try {
            service.saveDataToFiles();
            view.displaySaveSuccessBanner();
        } catch (FloorMasterPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void initialize() {
        try {
            service.initStates();
        } catch (FloorMasterPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            System.exit(-1);
        }

        try {
            service.initProducts();
        } catch (FloorMasterPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            System.exit(-1);
        }
        
        try {
            service.initDatabase();
        } catch (FloorMasterPersistenceException | FileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
            if(e.getClass() == FloorMasterPersistenceException.class){
                System.exit(-1);
            }
        }

        try {
            service.initConfig();
            view.displayIsTrainingBanner(service.isTraining());
        } catch (FloorMasterPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            System.exit(-1);
        }
        
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
