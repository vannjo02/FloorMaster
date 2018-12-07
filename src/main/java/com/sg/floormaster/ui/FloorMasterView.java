
package com.sg.floormaster.ui;

import com.sg.floormaster.dto.Order;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author Joshua Vannatter
 */
public class FloorMasterView {

    UserIO io;

    public FloorMasterView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
        io.println("*  <<Flooring Program>>");
        io.println("* 1. Display Orders");
        io.println("* 2. Add an Order");
        io.println("* 3. Edit an Orer");
        io.println("* 4. Remove an Orer");
        io.println("* 5. Save Database to Date-Formatted Files");
        io.println("* 6. Quit");
        io.println("*");
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");

        return io.readInt("Please select an option: ", 1, 6);
    }

    public LocalDate getDateSelection() {
        LocalDate date = parseDateResponse("Please enter date(-1 to exit): ");
        if (date == null){
            return null;
        }
        return date;
    }

    public int getIdQuery() {
        int id = io.readInt("Enter the order ID(-1 to exit): ");
        return id;
    }

    private LocalDate parseDateResponse(String prompt) {
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dateStr;
        while (true) {
            dateStr = io.readStr(prompt);
            if("-1".equals(dateStr)){
                return null;
            }
            try {
                return LocalDate.parse(dateStr, format1);
            } catch (Exception e) {
                try {
                    return LocalDate.parse(dateStr, format2);
                } catch (Exception e2) {
                    io.println("Invalid date format, try again.");
                }
            }
        }
    }

    public void displayOrderList(List<Order> orderList) {
        if(orderList.isEmpty()){
            io.println("\n=== It's a little empty in here 0.0 ===");
        } else {
            orderList.forEach((currentItem) -> {
                displayOrderInfo(currentItem);
            });
        }
        io.println();
    }

    public void displayOrderInfo(Order item) {
        if (item != null) {
            io.print(item.toJsonView());
        } else {
            io.print("No such item.");
        }
        io.println();
    }

    public Map<String, String> getNewOrderVals(Set<String> states, Set<String> products) {
        Map<String, String> vals = new HashMap<>();
        String name = io.readStr("Customer name: ");
        io.println("Valid states: " + states);
        String state = io.readStr("State: ").toUpperCase();
        io.println("Valid products: " + products);
        String product = io.readStr("Product Type: ");
        product = product.length() == 0 ? "" : product.substring(0, 1).toUpperCase() + product.substring(1).toLowerCase();
        String area = io.readStr("Area (sq ft): ");
        vals.put("name", name);
        vals.put("state", state);
        vals.put("product", product);
        vals.put("area", area);
        io.println("\n" + vals);
        if("y".equals(io.readStr("Would you like to add this order? (y): "))){
            return vals;
        }
        return null;
    }

    public Map<String, String> getEditOrderVals(Order order, Set<String> states, Set<String> products) {
        Map<String, String> vals = new HashMap<>();
        io.println("Enter new values, or leave blank to skip");
        String name = io.readStr("Customer name (" + order.getCustomerName() + "): ");
        io.println("Valid states: " + states);
        String state = io.readStr("State (" + order.getState() + "): ").toUpperCase();
        io.println("Valid products: " + products);
        String product = io.readStr("Product Type (" + order.getProductType() + "): ");
        product = product.length() == 0 ? "" : product.substring(0, 1).toUpperCase() + product.substring(1).toLowerCase();
        String area = io.readStr("Area (" + order.getArea() + " sq ft): ");
        
        vals.put("name", name);
        vals.put("state", state);
        vals.put("product", product);
        vals.put("area", area);
        return vals;
    }

    public boolean getRemoveConfirm() {
        String ans = io.readStr("Are you sure you want to delete this entry? (y/n): ");
        return "y".equals(ans);
    }

    public void displayOrdersBanner() {
        io.println("\n=== Displaying Orders ===");
    }

    public void displayAddOrderBanner() {
        io.println("\n=== Add a New Order ===");
    }

    public void displayEditOrderBanner() {
        io.println("\n=== Edit an Order ===");
    }

    public void displayEditOrderSuccessBanner() {
        io.println("\n=== Order Edited Successfully ===");
    }

    public void displayRemoveOrderBanner() {
        io.println("\n=== Remove an Order ===");
    }

    public void displayAddOrderSuccessBanner() {
        io.println("\n=== Order Was Added Successfully ===");
    }

    public void displayRemoveOrderSuccessBanner() {
        io.println("\n=== Order Was Removed Successfully ===");
    }
    
    public void displaySaveSuccessBanner() {
        io.println("\n=== Your Work Was Saved Successfully!");
    }

    public void displayActionCancelledBanner() {
        io.println("\n=== The Action Was Cancelled ===");
    }

    public void displayExitBanner() {
        io.println("\n=== Good Bye! ===");
    }

    public void displayUnknownCommandBanner() {
        io.println("\n=== Unknown Command!!! ===");
    }
    
    public void displayIsTrainingBanner(boolean isTraining) {
        if(isTraining){
            io.println("\n=== Initialized to training mode (no data persistence) ===");
        } else{
            io.println("\n=== Initialized to production mode (work will save automatically) ===");
        }
        
    }

    public void displayErrorMessage(String error) {
        io.println("ERROR: " + error);
    }
}
