package ru.job4j.chess;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * ChessTest.
 */
public class ChessTest {

    /**
     * Bishop test.
     */
    @Test
    public void whenMoveToOccupiedCellThenException() {
        Board board = new Board();
        Figure[] figures = {
                new Bishop(new Cell(0, 0)),
                new Bishop(new Cell(5, 5))
        };

        board.setFigures(figures);

        String result = "";
        try {
            Boolean resultAlt = board.move(new Cell(0, 0), new Cell(5, 5));
        } catch (Exception e) {
            result = e.toString();
        }

        String expected = (new OccupiedWayException("Клетка занята другой фигурой")).toString();
        assertThat(result, is(expected));
    }

    /**
     * Bishop test.
     */
    @Test
    public void whenMoveAcrossOccupiedCellThenException() {
        Board board = new Board();
        Figure[] figures = {
                new Bishop(new Cell(0, 0)),
                new Bishop(new Cell(4, 4)),
                new Bishop(new Cell(5, 5))
        };

        board.setFigures(figures);

        String result = "";
        try {
            Boolean resultAlt = board.move(new Cell(0, 0), new Cell(5, 5));
        } catch (Exception e) {
            result = e.toString();
        }

        String expected = (new OccupiedWayException("На пути стоят другие фигуры")).toString();
        assertThat(result, is(expected));
    }

    /**
     * Bishop test.
     */
    @Test
    public void whenFigureNotFoundThenException() {
        Board board = new Board();
        Figure[] figures = {
                new Bishop(new Cell(0, 0)),
                new Bishop(new Cell(5, 5))
        };

        board.setFigures(figures);

        String result = "";
        try {
            Boolean resultAlt = board.move(new Cell(0, 1), new Cell(5, 5));
        } catch (Exception e) {
            result = e.toString();
        }

        String expected = (new FigureNotFoundException("На исходной клетке нет фигуры")).toString();
        assertThat(result, is(expected));
    }

    /**
     * Bishop test.
     */
    @Test
    public void whenWrongWayThenException() {
        Board board = new Board();
        Figure[] figures = {
                new Bishop(new Cell(0, 1)),
                new Bishop(new Cell(5, 5))
        };

        board.setFigures(figures);

        String result = "";
        try {
            Boolean resultAlt = board.move(new Cell(0, 1), new Cell(5, 5));
        } catch (Exception e) {
            result = e.toString();
        }

        String expected = (new ImpossibleMoveException("Слон так не ходит")).toString();
        assertThat(result, is(expected));
    }

    /**
     * Bishop test.
     */
    @Test
    public void whenBishopCanGoThenItGoing() {
        Board board = new Board();
        Figure[] figures = {
                new Bishop(new Cell(0, 0))
        };

        board.setFigures(figures);

        try {
            board.move(new Cell(0, 0), new Cell(5, 5));
        } catch (Exception e) {
            System.out.println(e);
        }
        String result = board.getFigureOnCell(new Cell(5, 5)).toString();

        String expected = "Bishop 5:5";
        assertThat(result, is(expected));
    }



}
