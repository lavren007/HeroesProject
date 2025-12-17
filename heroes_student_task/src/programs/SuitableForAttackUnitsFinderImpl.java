package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        java.util.List<Unit> suitable = new java.util.ArrayList<>();
        for (List<Unit> row : unitsByRow) {
            for (int i = 0; i < row.size(); i++) {
                Unit current = row.get(i);
                if (current == null || !current.isAlive()) continue;
                boolean blocked = false;
                if (isLeftArmyTarget) {
                    if (i > 0 && row.get(i - 1) != null && row.get(i - 1).isAlive()) {
                        blocked = true;
                    }
                } else {
                    if (i < row.size() - 1 && row.get(i + 1) != null && row.get(i + 1).isAlive()) {
                        blocked = true;
                    }
                }
                if (!blocked) {
                    suitable.add(current);
                }
            }
        }
        return suitable;
    }
}
