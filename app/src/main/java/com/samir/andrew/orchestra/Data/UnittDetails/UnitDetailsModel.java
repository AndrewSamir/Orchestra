
package com.samir.andrew.orchestra.Data.UnittDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitDetailsModel {


    public UnitData unitData;
    public List<UnitPayment> unitPayment ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UnitDetailsModel(UnitData unitData, List<UnitPayment> unitPayment) {
        this.unitData = unitData;
        this.unitPayment = unitPayment;
    }
}
