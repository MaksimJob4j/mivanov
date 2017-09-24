package ru.job4j.chess;

/**
 * Board.
 */
public class Board {

    /**
     * Массив фигур на доске.
     */
    private Figure[] figures;

    /**
     *
     * @param source source.
     * @param dist dist.
     * @return boolean.
     * @throws ImpossibleMoveException ImpossibleMoveException.
     * @throws OccupiedWayException OccupiedWayException.
     * @throws FigureNotFoundException FigureNotFoundException.
     */
    boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {

        Figure figure = getFigureOnCell(source);

        if (figure != null) {
            for (Cell cell: figure.way(dist)) {
                if (getFigureOnCell(cell) != null) {
                    throw new OccupiedWayException("На пути стоят другие фигуры");
                }
            }

            if (getFigureOnCell(dist) != null) {
                throw new OccupiedWayException("Клетка занята другой фигурой");
            }

            for (int i = 0; i < figures.length; i++) {
                if (figures[i].getPosition().getIndex() == source.getIndex()) {
                    figures[i] = figures[i].clone(dist);

                }
            }
            return true;
        } else {
            throw new FigureNotFoundException("На исходной клетке нет фигуры");
        }
    }

    /**
     *
     * @param cell cell.
     * @return figure.
     */
    public Figure getFigureOnCell(Cell cell) {
        for (Figure figure: figures) {
            if (cell.getIndex() == figure.getPosition().getIndex()) {
                return figure;
            }
        }
        return null;
    }

//    8. Метод должен должен проверить
//   - Что в заданной ячейки есть фигура. если нет. то выкинуть исключение
//   - Если фигура есть. Проверить может ли она так двигаться. Если нет то упадет исключение
//   - Проверить что полученный путь. не занят фигурами. Если занят выкинуть исключение
//   - Если все отлично. Записать в ячейку новое новое положение Figure figure.clone(Cell dist)
//    9. Изначально сделайте. только движения фигуры слон и покажите промежуточный результат.




}
