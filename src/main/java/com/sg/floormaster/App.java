
package com.sg.floormaster;
/**
 *
 * @author Joshua Vannatter
 */
import com.sg.floormaster.controller.FloorMasterController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FloorMasterController controller = ctx.getBean("controller", FloorMasterController.class);
        controller.run();
    }
}
