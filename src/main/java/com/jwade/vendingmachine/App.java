
package com.jwade.vendingmachine;

import com.jwade.vendingmachine.controller.VendingMachineController;
import com.jwade.vendingmachine.dao.VendingMachinePersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jwade
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {

//        UserIO myIo = new UserIOImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingMachineDao myDao = new VendingMachineDaoImpl();
//        AuditDao myAuditDao = new AuditDaoImpl();
//        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);
//        VendingMachineController controller = new VendingMachineController(myView, myService);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
