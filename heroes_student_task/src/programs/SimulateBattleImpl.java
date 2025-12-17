package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog; // Позволяет логировать. Использовать после каждой атаки юнита

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        // Используем полные имена java.util.* классов
        java.util.List<Unit> allUnits = new java.util.ArrayList<>();
        allUnits.addAll(playerArmy.getUnits());
        allUnits.addAll(computerArmy.getUnits());

        while (true) {
            // Удаляем мёртвых
            allUnits.removeIf(u -> !u.isAlive());

            boolean playerAlive = false;
            for (Unit u : playerArmy.getUnits()) {
                if (u.isAlive()) {
                    playerAlive = true;
                    break;
                }
            }
            boolean computerAlive = false;
            for (Unit u : computerArmy.getUnits()) {
                if (u.isAlive()) {
                    computerAlive = true;
                    break;
                }
            }

            if (!playerAlive || !computerAlive) break;

            // Сортируем по убыванию атаки
            allUnits.sort((a, b) -> Integer.compare(b.getBaseAttack(), a.getBaseAttack()));

            // Создаем копию списка на текущий раунд
            java.util.List<Unit> roundUnits = new java.util.ArrayList<>(allUnits);
            for (Unit unit : roundUnits) {
                if (!unit.isAlive()) continue;
                Unit target = unit.getProgram().attack();
                if (target != null && printBattleLog != null) {
                    printBattleLog.printBattleLog(unit, target);
                }
            }
        }
    }
}