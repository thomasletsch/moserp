package org.moserp.inventory.repository;

import org.moserp.inventory.rest.InventoryItemLinks;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = InventoryItemLinks.class)
public class InventoryIntegrationTestContext {

}