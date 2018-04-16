
package undoapplet;

import com.sun.java.swing.undo.*;
import com.sun.java.swing.*;



/**
* Same as AddEdit. The RemoveEdit record chnages which
* occurs after performing an remove action
*
* @author Tomer Meshorer
*/

public class RemoveEdit extends AbstractUndoableEdit {

   private Object element_;
   private int index_;
   private DefaultListModel model_;

   public RemoveEdit ( DefaultListModel model,Object element, int index ) {
    	model_ = model;
       	element_ = element;
       	index_=index;
   }

   public void undo() throws CannotUndoException {
       model_.insertElementAt( element_, index_);
   }

   public void redo() throws CannotRedoException {
   	model_.removeElementAt( index_);
   }

  public boolean canUndo() {
         return true;
  }

  public boolean canRedo() {
         return true;
  }

  public String getPresentationName() {
         return "Remove";
  }

}


