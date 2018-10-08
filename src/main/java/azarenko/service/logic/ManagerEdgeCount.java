package azarenko.service.logic;

import azarenko.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static azarenko.entity.ButtClose.BUTT_X;

@Component
public class ManagerEdgeCount {

    private ConcurrentHashMap<BigDecimal, Double> map = new ConcurrentHashMap<>();

    public ConcurrentHashMap<BigDecimal, Double> getLengthEdgeMaterialForOrder(Order order) {
        List<Module> moduleList = order.getModuleList();
        List<Detail> detailList = order.getDetailList();
        if (Objects.nonNull(moduleList)) {
            for (Module module : moduleList) {
                map = getLengthEdgeMaterialForModule(module);
            }
        }
        if (Objects.nonNull(detailList)) {
            for (Detail detail : detailList) {
                map.putAll(getLengthEdgeMaterialForDetailList(detailList));
            }
        }
        return map;
    }

    public ConcurrentHashMap<BigDecimal, Double> getLengthEdgeMaterialForModule(Module module) {
        return getLengthEdgeMaterialForDetailList(module.getDetailList());
    }

    public ConcurrentHashMap<BigDecimal, Double> getLengthEdgeMaterialForDetailList(List<Detail> detailList) {
        if (Objects.nonNull(detailList)) {
            for (Detail detail : detailList) {
                map = getLengthEdgeMaterialForDetail(detail);
            }
        }
        return map;
    }

    public ConcurrentHashMap<BigDecimal, Double> getLengthEdgeMaterialForDetail(Detail detail) {
        return getButtClose(detail.getX(), detail.getY(), detail.getEdgeMaterial(), detail.getCount());
    }

    private ConcurrentHashMap<BigDecimal, Double> getButtClose(int x, int y, List<EdgeMaterial> edgeMaterials, int count) {
        if (Objects.nonNull(edgeMaterials)) {
            for (EdgeMaterial material : edgeMaterials) {
                map.merge(material.getPrice(), getlength(x, y, material, count), (oldValue , newValue) -> oldValue + newValue);
            }
        }
        return map;
    }

    private double getlength(int x, int y, EdgeMaterial material, int count) {
        switch (material.getButtCloses()) {
            case BUTT_X:
                return (double) x *count;
            case BUTT_Y:
                return (double) y * count;
            case BUTT_DOUBLE_X:
                return (double) x * 2 * count;
            case BUTT_DOUBLE_Y:
                return (double) y * 2 * count;
            case BUTT_X_AND_Y:
                return (double) x + y * count;
            case BUTT_ARROUND:
                return (double) x * 2 + y * 2 * count;
            case BUTT_DOUBLE_X_AND_Y:
                return (double) x * 2 + y * count;
            case BUTT_DOUBLE_Y_AND_X:
                return (double) y * 2 + x * count;
        }
        return 0d;
    }
}
