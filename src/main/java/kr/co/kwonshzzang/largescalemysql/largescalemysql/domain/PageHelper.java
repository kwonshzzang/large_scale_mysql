package kr.co.kwonshzzang.largescalemysql.largescalemysql.domain;

import org.springframework.data.domain.Sort;

public class PageHelper {
    public static String orderBy(Sort sort) {
        if(sort.isEmpty()) {
            return "id DESC";
        }


        System.out.println(sort);


        var orderBys = sort.stream()
                .map(order -> order.getProperty() + " " + order.getDirection())
                .toList();


        String v = String.join(", " , orderBys);

        return String.join(", " , orderBys);
    }
}
