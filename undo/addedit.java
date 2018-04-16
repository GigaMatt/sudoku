
package undoapplet;

import com.sun.java.swing.undo.*;
import com.sun.java.swing.*;



/**
* The Add Edit class record the changes occured to the
* list after performing an add action. The add edit support
* undo / redo of add action.
*
* @author Tomer Meshorer
*/

public class AddEdit extends AbstractUndoableEdit {

  private Object element_;
  private int index_;
  private DefaultListModel model_;

  public AddEdit( DefaultListModel model, Object element, int index ) {
     model_=model;
     element_ = element;
     index_=index;
  }

  public void undo() throws CannotUndoException {
     model_.removeElementAt( index_ );
  }

  public void redo() throws CannotRedoException {
     model_.insertElementAt( element_, index_ );
  }

  public boolean canUndo() {
     return true;
  }

  public boolean canRedo() {
     return true;
  }

  public String getPresentationName() {
     return "Add";
  }

}


