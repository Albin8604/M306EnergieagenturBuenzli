package ch.gruppe.d.energieagentur.util.ui;

import ch.gruppe.d.energieagentur.util.Validator;
import ch.gruppe.d.energieagentur.util.ui.annotation.TableCol;
import ch.gruppe.d.energieagentur.util.ui.annotation.TableIgnore;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TableManager {
    /**
     * Will automatically set debug mode to false
     * <p>
     * how to use:
     * TableManager.initColumns(
     * data,table
     * );
     *
     * @param data      Date to be added to the Table
     * @param tableView Table
     * @param <T>       for which dataclass this table should be initialized for
     */
    public static <T> void initColumns(ObservableList<T> data, TableView<T> tableView) {
        initColumns(data, tableView, false);
    }

    /**
     * how to use:
     * TableManager.initColumns(
     * data,table,debug
     * );
     *
     * @param data      Date to be added to the Table
     * @param tableView Table
     * @param debug     if in debug mode (just prints more details while initializing table)
     * @param <T>       for which dataclass this table should be initialized for
     */
    public static <T> void initColumns(ObservableList<T> data, TableView<T> tableView, boolean debug) {
        if (Validator.isNullOrEmpty(data)) {
            throw new RuntimeException("Data not provided");
        }

        if (Validator.isNullOrEmpty(tableView.getColumns())) {
            throw new RuntimeException("Table has 0 columns");
        }

        List<Member> membersOfClazz = getMembersOfClazz(data.get(0).getClass());

        try {
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                Member member = null;

                for (Member memberIteration : membersOfClazz) {
                    if (
                            memberIteration.getName().equalsIgnoreCase(column.getText()) ||
                                    memberIteration.getName().substring(3).equalsIgnoreCase(column.getText())
                    ) {
                        member = memberIteration;
                        membersOfClazz.remove(memberIteration);
                        break;
                    } else if (memberIteration instanceof Method) {
                        if (((Method) memberIteration).getAnnotation(TableCol.class).forCol().equalsIgnoreCase(column.getText())) {
                            member = memberIteration;
                        }
                    }
                }

                if (member == null) {
                    throw new RuntimeException("Field not found for TableCol " + column.getText());
                }

                column.setCellValueFactory(new PropertyValueFactory<>(
                        getExtractedNameOfMember(member)
                ));

                if (debug) {
                    System.out.println("--------------------");
                    System.out.print("Column: ");
                    System.out.println(column.getText());

                    System.out.print("Value Factory Value: ");
                    System.out.println(member.getName());
                    System.out.println("--------------------");
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(data.get(0).getClass().getName() + " less declared fields for TableCols");
        }

        tableView.setItems(data);
    }

    private static List<Member> getMembersOfClazz(Class<?> clazz) {
        List<Member> membersOfClazz = new java.util.ArrayList<>(Arrays.stream(
                        clazz.getDeclaredMethods()
                ).filter(method -> method.getAnnotation(TableCol.class) != null)
                .toList());

        membersOfClazz.addAll(new java.util.ArrayList<>(Arrays.stream(
                        clazz.getDeclaredFields()
                ).filter(field -> field.getAnnotation(TableIgnore.class) == null)
                .toList()));

        return membersOfClazz;
    }

    private static String getExtractedNameOfMember(Member member) {
        if (member instanceof Method) {
            return extractLowerCastMethodName(member);
        } else {
            return extractLowerCastName(member);
        }
    }

    private static String extractLowerCastName(Member member) {
        return (String.valueOf(member.getName().charAt(0))).toLowerCase() + member.getName().substring(1);
    }

    private static String extractLowerCastMethodName(Member member) {
        String name = member.getName().substring(3);
        return (String.valueOf(name.charAt(0))).toLowerCase() + name.substring(1);
    }
}
