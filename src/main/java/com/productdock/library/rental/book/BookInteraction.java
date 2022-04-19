package com.productdock.library.rental.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInteraction implements Serializable {
    private final static long serialVersionUID = 11111L;
    private String userEmail;
    private Date date;
}
