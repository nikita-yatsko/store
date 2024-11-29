package com.electronicsstore.store.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.Arrays;

@Service
public class ProductDataServiceImpl implements ProductDataService {

    @Override
    public void populateProductData(Model model) {
        model.addAttribute("types", Arrays.asList("Ноутбук"));
        model.addAttribute("screens", Arrays.asList("Сенсорный", "Несенсорный"));
        model.addAttribute("screenResolutions", Arrays.asList("1600x900", "1920x1080", "1920x1200", "2560x1440", "2560x1600", "2880x1800"));
        model.addAttribute("screenTechnologys", Arrays.asList("IPS", "TN+Film", "OLED"));
        model.addAttribute("ramCapacitys", Arrays.asList("8", "16", "32", "64"));
        model.addAttribute("hddCapacitys", Arrays.asList("нет", "256", "512", "1024"));
        model.addAttribute("ssdCapacitys", Arrays.asList("нет", "256", "512", "1024"));
        model.addAttribute("operationSystems", Arrays.asList("Linux", "Windows 10", "Windows 10 Pro", "Windows 11", "Windows 11 Pro", "без OC", "Mac OS"));
    }
}
