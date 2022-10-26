
package com.jwade.vendingmachine;

import com.jwade.vendingmachine.controller.VendingMachineController;
import com.jwade.vendingmachine.dao.*;
import com.jwade.vendingmachine.service.VendingMachineService;
import com.jwade.vendingmachine.service.VendingMachineServiceImpl;
import com.jwade.vendingmachine.ui.UserIO;
import com.jwade.vendingmachine.ui.UserIOImpl;
import com.jwade.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author jwade
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {

        UserIO myIo = new UserIOImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        AuditDao myAuditDao = new AuditDaoImpl();
        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);
        VendingMachineController controller = new VendingMachineController(myView, myService);
        controller.run();
    }
}
