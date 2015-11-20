package org.moserp.inventory;

import android.databinding.BaseObservable;
import android.text.TextWatcher;

import org.moserp.common.databinding.BaseTextWatcher;
import org.moserp.common.databinding.SpinnerValueBinding;
import org.moserp.common.domain.Quantity;
import org.moserp.environment.Facility;
import org.moserp.product.Product;

public class OutgoingDeliveryViewModel extends BaseObservable {

    private Product product;
    private Facility fromFacility;
    private Quantity quantity;

    public OutgoingDeliveryViewModel() {
    }

    public Facility getFromFacility() {
        return fromFacility;
    }

    public void setFromFacility(Facility fromFacility) {
        this.fromFacility = fromFacility;
        notifyPropertyChanged(BR.outgoingDeliveryViewModel);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyPropertyChanged(BR.outgoingDeliveryViewModel);
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
        return "OutgoingDeliveryViewModel{" +
                "product=" + product +
                ", fromFacility=" + fromFacility +
                ", quantity=" + quantity +
                '}';
    }

    public OutgoingDelivery toOutgoingDelivery() {
        OutgoingDelivery outgoingDelivery = new OutgoingDelivery();
        outgoingDelivery.setFromFacility(fromFacility);
        DeliveryItem deliveryItem = new DeliveryItem();
        deliveryItem.getProductInstance().setProduct(product);
        deliveryItem.setQuantity(quantity);
        outgoingDelivery.add(deliveryItem);
        return outgoingDelivery;
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

    public SpinnerValueBinding<Facility> getFacilityBinding() {
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

}
