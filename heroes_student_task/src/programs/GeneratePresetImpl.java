package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        // Army принимает List<Unit> в конструкторе
        Army army = new Army(new ArrayList<>());
        List<Unit> sortedUnits = new ArrayList<>(unitList);
        sortedUnits.sort((a, b) -> {
            double effA = (double) a.getBaseAttack() / a.getCost();
            double effB = (double) b.getBaseAttack() / b.getCost();
            return Double.compare(effB, effA);
        });
        int remainingPoints = maxPoints;
        Map<String, Integer> typeCount = new HashMap<>();
        for (Unit unit : sortedUnits) {
            int maxPerType = 11;
            String type = unit.getUnitType();
            typeCount.putIfAbsent(type, 0);
            int canAdd = Math.min(remainingPoints / unit.getCost(), maxPerType - typeCount.get(type));
            if (canAdd > 0) {
                for (int i = 0; i < canAdd; i++) {
                    Unit copy = new Unit(
                            unit.getName() + "_" + i,
                            unit.getUnitType(),
                            unit.getHealth(),
                            unit.getBaseAttack(),
                            unit.getCost(),
                            unit.getAttackType(),
                            unit.getAttackBonuses(),
                            unit.getDefenceBonuses(),
                            -1, -1
                    );
                    army.getUnits().add(copy);
                    remainingPoints -= unit.getCost();
                }
                typeCount.put(type, typeCount.get(type) + canAdd);
            }
        }
        army.setPoints(maxPoints - remainingPoints);
        return army;
    }
}