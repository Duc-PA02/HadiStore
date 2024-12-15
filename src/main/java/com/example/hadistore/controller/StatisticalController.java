package com.example.hadistore.controller;

import com.example.hadistore.dtos.response.CategoryBestSellerResponse;
import com.example.hadistore.dtos.response.StatisticalResponse;
import com.example.hadistore.entity.Order;
import com.example.hadistore.entity.Product;
import com.example.hadistore.enums.OrderStatus;
import com.example.hadistore.repository.OrderRepository;
import com.example.hadistore.repository.ProductRepository;
import com.example.hadistore.repository.StatisticalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/statistical")
@RequiredArgsConstructor
public class StatisticalController {
    private final StatisticalRepository statisticalRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @GetMapping("{year}")
    public ResponseEntity<List<StatisticalResponse>> getStatisticalYear(@PathVariable("year") int year) {
        List<Object[]> list = statisticalRepository.getMonthOfYear(year);
        List<StatisticalResponse> listSta = new ArrayList<>();
        List<StatisticalResponse> listReal = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StatisticalResponse sta = new StatisticalResponse((int) list.get(i)[1], null, (Double) list.get(i)[0], 0);
            listSta.add(sta);
        }
        for (int i = 1; i < 13; i++) {
            StatisticalResponse sta = new StatisticalResponse(i, null, 0.0, 0);
            for (int y = 0; y < listSta.size(); y++) {
                if (listSta.get(y).getMonth() == i) {
                    listReal.remove(sta);
                    listReal.add(listSta.get(y));
                    break;
                } else {
                    listReal.remove(sta);
                    listReal.add(sta);
                }
            }
        }
        return ResponseEntity.ok(listReal);
    }

    @GetMapping("/countYear")
    public ResponseEntity<List<Integer>> getYears() {
        return ResponseEntity.ok(statisticalRepository.getYears());
    }

    @GetMapping("/revenue/year/{year}")
    public ResponseEntity<Double> getRevenueByYear(@PathVariable("year") int year) {
        return ResponseEntity.ok(statisticalRepository.getRevenueByYear(year));
    }

    @GetMapping("/get-all-order-success")
    public ResponseEntity<List<Order>> getAllOrderSuccess() {
        return ResponseEntity.ok(orderRepository.findByStatus(OrderStatus.SUCCESS.getValue()));
    }

    @GetMapping("/get-category-seller")
    public ResponseEntity<List<CategoryBestSellerResponse>> getCategoryBestSeller() {
        List<Object[]> list = statisticalRepository.getCategoryBestSeller();
        List<CategoryBestSellerResponse> listCategoryBestSeller = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CategoryBestSellerResponse categoryBestSeller = new CategoryBestSellerResponse(String.valueOf(list.get(i)[1]),
                    Integer.parseInt(String.valueOf(list.get(i)[0])), Double.valueOf(String.valueOf(list.get(i)[2])));
            listCategoryBestSeller.add(categoryBestSeller);
        }
        return ResponseEntity.ok(listCategoryBestSeller);
    }

    @GetMapping("/get-inventory")
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productRepository.findByStatusTrueOrderByQuantityDesc());
    }
}
