package com.electronicsstore.store.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Long> productIds = new ArrayList<>();
}
