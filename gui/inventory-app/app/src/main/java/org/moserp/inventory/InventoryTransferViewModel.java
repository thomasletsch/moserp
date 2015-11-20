package org.moserp.inventory;

import android.databinding.BaseObservable;
import android.text.TextWatcher;

import org.moserp.common.databinding.BaseTextWatcher;
import org.moserp.common.databinding.SpinnerValueBinding;
import org.moserp.common.domain.Quantity;
import org.moserp.environment.Facility;
import org.moserp.product.Product;

public class InventoryTransferViewModel extends BaseObservable {

    private Product product;
    private Facility fromFacility;
    private Facility toFacility;
    private Quantity quantity;

    public InventoryTransferViewModel() {
    }

    public Facility getFromFacility() {
        return fromFacility;
    }

    public void setFromFacility(Facility fromFacility) {
        this.fromFacility = fromFacility;
        notifyPropertyChanged(BR.inventoryTransferViewModel);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyPropertyChanged(BR.inventoryTransferViewModel);
    }

    public Facility getToFacility() {
        return toFacility;
    }

    public void setToFacility(Facility toFacility) {
        this.toFacility = toFacility;
        notifyPropertyChanged(BR.inventoryTransferViewModel);
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public TextWatcher getQuantityWatcher() {
        return new BaseTextWatcher() {
            public void onTextChanged(String text) {
                quantity = Quantity.convertStringToQuantity(text);
            }
        };
    }

    @Override
    public String toString() {
        return "InventoryTransferViewModel{" +
                "product=" + product +
                ", fromFacility=" + fromFacility +
                ", toFacility=" + toFacility +
                ", quantity=" + quantity +
                '}';
    }

    public InventoryTransfer toInventoryTransfer() {
        InventoryTransfer inventoryTransfer = new InventoryTransfer();
        inventoryTransfer.setFromFacility(fromFacility);
        inventoryTransfer.setToFacility(toFacility);
        inventoryTransfer.getProductInstance().setProduct(product);
        inventoryTransfer.setQuantity(quantity);
        return inventoryTransfer;
    }

    public SpinnerValueBinding<Facility> getToFacilityBinding() {
        return new SpinnerValueBinding<Facility>() {
            @Override
            public void setValue(Facility facility) {
                setToFacility(facility);
            }

            @Override
            public Facility getValue() {
                return getToFacility();
            }
        };
    }
    public SpinnerValueBinding<Facility> getFromFacilityBinding() {
        return new SpinnerValueBinding<Facility>() {
            @Override
            public void setValue(Facility facility) {
                setFromFacility(facility);
            }

            @Override
            public Facility getValue() {
                return getFromFacility();
            }
        };
    }
    public SpinnerValueBinding<Product> getProductBinding() {
        return new SpinnerValueBinding<Product>() {
            @Override
            public void setValue(Product product) {
                setProduct(product);
            }

            @Override
            public Product getValue() {
                return getProduct();
            }
        };
    }
}
