
package com.jwade.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jwade
 */
public class AuditDaoImpl implements AuditDao{

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException{

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e){
            throw new VendingMachinePersistenceException(
                    "Could not persist audit information", e
            );
        }

        ZonedDateTime timestamp = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd kk:mm:ss zzz yyyy");
        String formatDateTime = timestamp.format(formatter);
        out.println( formatDateTime + " :: " + entry);
        out.flush();
    }
    
}
