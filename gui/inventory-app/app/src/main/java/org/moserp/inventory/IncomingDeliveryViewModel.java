package org.moserp.inventory;

import android.databinding.BaseObservable;
import android.text.TextWatcher;

import org.moserp.common.databinding.BaseTextWatcher;
import org.moserp.common.databinding.SpinnerValueBinding;
import org.moserp.common.domain.Quantity;
import org.moserp.environment.Facility;
import org.moserp.product.Product;

public class IncomingDeliveryViewModel extends BaseObservable {

    private Product product;
    private Facility toFacility;
    private Quantity quantity;

    public IncomingDeliveryViewModel() {
    }

    public Facility getToFacility() {
        return toFacility;
    }

    public void setToFacility(Facility toFacility) {
        this.toFacility = toFacility;
        notifyPropertyChanged(BR.incomingDeliveryViewModel);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyPropertyChanged(BR.incomingDeliveryViewModel);
    }

    public String getQuantity() {
        return Quantity.convertQuantityToString(quantity);
    }

    public void setQuantity(String quantity) {
        this.quantity = Quantity.convertStringToQuantity(quantity);
        notifyPropertyChanged(BR.incomingDeliveryViewModel);
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
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
        return "IncomingDeliveryViewModel{" +
                "product=" + product +
                ", toFacility=" + toFacility +
                ", quantity=" + quantity +
                '}';
    }

    public IncomingDelivery toIncomingDelivery() {
        IncomingDelivery incomingDelivery = new IncomingDelivery();
        incomingDelivery.setToFacility(toFacility);
        DeliveryItem deliveryItem = new DeliveryItem();
        deliveryItem.getProductInstance().setProduct(product);
        deliveryItem.setQuantity(quantity);
        incomingDelivery.add(deliveryItem);
        return incomingDelivery;
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
}
