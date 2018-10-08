package azarenko.service.logic;

import azarenko.entity.Detail;
import azarenko.entity.Module;
import azarenko.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ManagerQuadCount {

    public double getCountSquareDetail(int x, int y,int count) {
        return (((double)x / 1000) * ((double) y / 1000)) * count;
    }

    public Map<BigDecimal,Double> getCountSquareDetailsList(List<Detail> detailList) {
        Map<BigDecimal,Double> map = new HashMap<>();
        if (Objects.nonNull(detailList)) {
            for (Detail detail : detailList) {
                map.merge(detail.getColorMaterial().getPrice(), getCountSquareDetail(detail.getX(),detail.getY(),detail.getCount()),(a,b) -> a + b);
            }
        }
        return map;
    }

    public Map<BigDecimal,Double> getCountSquareDetailsForModule(Module module) {
        Map<BigDecimal,Double> map = new HashMap<>();
        if (Objects.nonNull(module)) {
            List<Detail> detailList = module.getDetailList();
            if (Objects.nonNull(detailList)) {
               map = getCountSquareDetailsList(detailList);
            }
        }
        return map;
    }

    public Map<BigDecimal,Double> getCountSquareForOrder(Order order) {
        Map<BigDecimal,Double> map = new HashMap<>();
        if (Objects.nonNull(order)) {
            List<Detail> detailList = order.getDetailList();
            List<Module> moduleList = order.getModuleList();
            if (Objects.nonNull(detailList)) {
               map = getCountSquareDetailsList(detailList);
            }
            if (Objects.nonNull(moduleList)) {
                //map.merge getCountSquareDetailsForModuleList(moduleList);
            }
        }
        return map;
    }

    public Map<BigDecimal,Double> getCountSquareDetailsForModuleList(List<Module> moduleList) {
        Map<BigDecimal,Double> map = new HashMap<>();
        if (Objects.nonNull(moduleList)) {
            for (Module module : moduleList) {
                map = getCountSquareDetailsForModule(module);
            }
        }
        return map;
    }
}
