package com.example.hadistore.repository;

import com.example.hadistore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticalRepository extends JpaRepository<Product, Long> {
    @Query(value = "select sum(amount), month(order_date) from orders where year(order_date) = ? and status = 2 group by month(order_date)", nativeQuery = true)
    List<Object[]> getMonthOfYear(int year);

    @Query(value = "select year(order_date) from orders group by year(order_date)", nativeQuery = true)
    List<Integer> getYears();

    @Query(value = "select sum(amount) from orders where year(order_date) = ? and status = 2", nativeQuery = true)
    Double getRevenueByYear(int year);

    @Query(value = "select \n" +
            "    sum(p.sold) as total_sold, \n" +
            "    c.name as category_name, \n" +
            "    (avg(p.price) * sum(p.sold) - (avg(p.discount) * sum(p.sold))) as total_revenue\n" +
            "from category c\n" +
            "join product p on p.category_id = c.category_id\n" +
            "group by c.name\n" +
            "order by total_sold desc;", nativeQuery = true)
    List<Object[]> getCategoryBestSeller();
}
