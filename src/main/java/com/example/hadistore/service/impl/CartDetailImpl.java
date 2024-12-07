package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.CartDetailRequest;
import com.example.hadistore.entity.Cart;
import com.example.hadistore.entity.CartDetail;
import com.example.hadistore.entity.Product;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.CartDetailRepository;
import com.example.hadistore.repository.CartRepository;
import com.example.hadistore.repository.ProductRepository;
import com.example.hadistore.service.CartDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartDetailImpl implements CartDetailService {
    CartDetailRepository cartDetailRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    @Override
    public List<CartDetail> findAllCartDetailByCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new DataNotFoundException("Cart not found"));
        return cartDetailRepository.findByCart(cart);
    }

    @Override
    public CartDetail findById(Long id) {
        return cartDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("CartDetail not found"));
    }

    @Override
    public CartDetail addItem(CartDetail cartDetail) {
        Cart cart = cartRepository.findById(cartDetail.getCart().getCartId())
                .orElseThrow(() -> new DataNotFoundException("Cart not found"));
        Product product = productRepository.findByProductIdAndStatusTrue(cartDetail.getProduct().getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        List<Product> productList = productRepository.findByStatusTrue();
        boolean check = false;
        for (Product p : productList){
            if (p.getProductId() == product.getProductId()){
                check = true;
            }
        }
        if (!check){
            return null;
        }
        List<CartDetail> cartDetailList = cartDetailRepository.findByCart(cart);
        for (CartDetail item : cartDetailList){
            if (item.getProduct().getProductId() == cartDetail.getProduct().getProductId()){
                item.setQuantity(item.getQuantity()+1);
                item.setPrice(item.getPrice() + cartDetail.getPrice());
                CartDetail updatedCartDetail = cartDetailRepository.save(item);
                updateCartAmount(cart);
                return updatedCartDetail;
            }
        }
        CartDetail newCartDetail = cartDetailRepository.save(cartDetail);
        updateCartAmount(cart);
        return newCartDetail;
    }

    @Override
    public CartDetail updateDetail(CartDetailRequest cartDetailRequest) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailRequest.getCartDetailId())
                .orElseThrow(() -> new DataNotFoundException("Cart Detail not found"));
        if (cartDetailRequest.getQuantity() < 1) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        Product product = cartDetail.getProduct();
        if (cartDetailRequest.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Số lượng vượt quá số lượng tồn kho");
        }
        cartDetail.setQuantity(cartDetailRequest.getQuantity());
        cartDetail.setPrice(cartDetailRequest.getPrice());
        cartDetailRepository.save(cartDetail);
        updateCartAmount(cartDetail.getCart());
        return cartDetail;
    }

    @Override
    public void deleteItem(Long id) {
        CartDetail cartDetail = cartDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Item not found"));
        cartDetailRepository.delete(cartDetail);
    }
    private void updateCartAmount(Cart cart) {
        List<CartDetail> cartDetailList = cartDetailRepository.findByCart(cart);
        double totalAmount = cartDetailList.stream()
                .mapToDouble(CartDetail::getPrice)
                .sum();
        cart.setAmount(totalAmount);
        cartRepository.save(cart);
    }
}
