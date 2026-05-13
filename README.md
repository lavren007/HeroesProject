# Heroes Project - Реализация алгоритмов для пошаговой стратегии.

## Описание проекта:
Реализованы 4 ключевых алгоритма для игры Heroes:
1. `GeneratePreset` - генерация армии противника
2. `SimulateBattle` - симуляция пошагового боя
3. `SuitableForAttackUnitsFinder` - поиск доступных целей
4. `UnitTargetPathFinder` - поиск кратчайшего пути

## 📊 Алгоритмическая сложность:

### 1. `GeneratePresetImpl.generate()`
- **Сложность**: O(n log n), где n = количество типов юнитов (4)
- **Доказательство**:
    - Сортировка юнитов по эффективности: O(n log n)
    - Заполнение армии: O(n * m), где m ≤ 11 (макс. юнитов типа)
    - Итог: O(n log n) + O(n * m) = O(n log n)

### 2. `SimulateBattleImpl.simulate()`
- **Сложность**: O(r * n log n), где r = раунды, n = юниты
- **Доказательство**:
    - Каждый раунд: сортировка O(n log n)
    - Проход по всем юнитам: O(n)
    - В худшем случае: O(n² log n), если каждый юнит погибает по одному за раунд

### 3. `SuitableForAttackUnitsFinderImpl.getSuitableUnits()`
- **Сложность**: O(rows * cols), фактически O(n)
- **Доказательство**:
    - Двойной цикл по рядам (3) и колонкам (переменное)
    - Каждая проверка за O(1)
    - Итог: O(3 * m) = O(m) ~ O(n)

### 4. `UnitTargetPathFinderImpl.getTargetPath()`
- **Сложность**: O((W*H) log(W*H))
- **Доказательство**:
    - Поле 27x21 = 567 узлов
    - Алгоритм A* с PriorityQueue: O(V log V)
    - V = W*H = 567, log 567 ≈ 9.1
    - Итог: O(567 * 9.1) ≈ O(5100 операций)

### Сборка JAR:
1. Откройте проект в IntelliJ IDEA
2. File → Project Structure → Artifacts
3. Нажмите "+" → JAR → From modules with dependencies
4. Главный класс оставьте пустым
5. Build → Build Artifacts → Build
6. JAR появится в `out/artifacts/HeroesProject_jar/`

### Тестирование в игре:
1. Распакуйте `heroes.rar`
2. Скопируйте ваш JAR в `heroes/jars/`, заменив `obf.jar`
3. Запустите игру: `java -jar "Heroes Battle-1.0.0.jar"`
