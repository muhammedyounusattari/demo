package com.ticket.viewers.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Products {

    private Long id;
    private String title;
    private int price;
    private String description;
    private String category;
    private String image;

}
