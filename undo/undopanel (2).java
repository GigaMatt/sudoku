package undoapplet;

import com.sun.java.swing.*;
import com.sun.java.swing.border.*;
import com.sun.java.swing.undo.*;
import com.sun.java.swing.event.*;


import java.awt.event.*;
import java.awt.*;

/**
* The undo applet defines the undo / redo concpet
* in swing. The applet allow you to add / remove
* object to/from a list. It also support undoing/
* redoing those operations
*
*@author Tomer Meshorer
*/



public class UndoPanel extends JPanel {
   JList elementList_;             // The list
   DefaultListModel elementModel_; // The list model
   JScrollPane scrollPane_;
   JButton addElementBtn_ ;
   JButton removeElementBtn_ ;
   JButton undoBtn_;
   JButton redoBtn_;
   int _lastElementID;

   /**
   * undo system elements
   */
   UndoManager undoManager_;         // history list
   UndoableEditSupport undoSupport_; // event support


   public UndoPanel() {
      elementModel_ = new DefaultListModel();
      elementList_ = new JList(elementModel_);
      scrollPane_ = new JScrollPane(elementList_);
      elementList_.addListSelectionListener(new ListSelectionAdapter());

      // create the actions
      Action addAction  = new AddAction();
      Action removeAction = new RemoveAction();
      Action undoAction = new UndoAction();
      Action redoAction = new RedoAction();

      // create the buttons and register the actions
      addElementBtn_ = new JButton("Add Foo");
      addElementBtn_.addActionListener(addAction);
      removeElementBtn_ = new JButton("remove Foo");
      removeElementBtn_.addActionListener(removeAction);
      removeElementBtn_.setEnabled(false);
      undoBtn_ = new JButton("undo");
      undoBtn_.addActionListener(undoAction);
      redoBtn_ = new JButton("redo");
      redoBtn_.addActionListener(redoAction);

      // create the undo / redo panel
      JPanel undoPanel = new JPanel();
      undoPanel.setLayout(new GridLayout(1,2));
      undoPanel.setBorder(
                  BorderFactory.createTitledBorder(
                  LineBorder.createBlackLineBorder(),
                  "Undo/ Redo",
                  TitledBorder.LEFT,
                  TitledBorder.TOP));
      undoPanel.add(undoBtn_);
      undoPanel.add(redoBtn_);

      // create the actions panel
      JPanel actionPanel = new JPanel();
      actionPanel.setLayout(new GridLayout(1,2));
      actionPanel.setBorder(
                  BorderFactory.createTitledBorder(
                  LineBorder.createBlackLineBorder(),
                  "Actions",
                  TitledBorder.LEFT,
                  TitledBorder.TOP));
      actionPanel.add(addElementBtn_);
      actionPanel.add(removeElementBtn_);

      // create the task panel
      JPanel taskPanel= new JPanel();
      taskPanel.setLayout(new GridLayout(1,2));
      taskPanel.add(actionPanel);
      taskPanel.add(undoPanel);
      taskPanel.setSize(100,100);

      // add the task panel to the content pane
      setLayout(new BorderLayout());
      add(BorderLayout.SOUTH,taskPanel);
      add(BorderLayout.CENTER,scrollPane_);

      // initilize the undo.redo system
      undoManager_= new UndoManager();
      undoSupport_ = new UndoableEditSupport();
      undoSupport_.addUndoableEditListener(new UndoAdapter());
      refreshUndoRedo();
  }


  /**
  * This method is called after each undoable operation
  * in order to refresh the presentation state of the
  * undo/redo GUI
  */

  public void refreshUndoRedo() {

     // refresh undo
     undoBtn_.setText( undoManager_.getUndoPresentationName() );
     undoBtn_.setEnabled( undoManager_.canUndo() );

     // refresh redo
     redoBtn_.setText( undoManager_.getRedoPresentationName() );
     redoBtn_.setEnabled( undoManager_.canRedo() );
  }


  /**
  * Add new element Action.
  */
  private class AddAction extends AbstractAction {

      public void actionPerformed(ActionEvent evt) {
          // always add to the end of the JList
          int NumOfElements = elementModel_.getSize();
          // however, give the the element is ID number
          Object element = new String("Foo " + _lastElementID);

          // record the effect
          UndoableEdit edit = new AddEdit(elementModel_,
                                     element, NumOfElements );
          // perform the operation
          elementModel_.addElement( element );

          // notify the listeners
          undoSupport_.postEdit( edit );

          // increment the ID
          _lastElementID ++ ;

     }
  }

  /**
  * The remove action
  */
  private class RemoveAction extends AbstractAction {

      public void actionPerformed( ActionEvent evt ) {
         int selectIdx = elementList_.getSelectedIndex();
         Object selectedElement = elementModel_.getElementAt( selectIdx );

         // create the edit
         UndoableEdit edit_ = new RemoveEdit( elementModel_,
                                     selectedElement, selectIdx );

         //perfrom the remove
         elementModel_.removeElementAt( selectIdx );

         // notify the listeners
       	 undoSupport_.postEdit( edit_ );

       	 //disable the remove button
       	 removeElementBtn_.setEnabled( false );

    }
  }

  /**
  *  undo action
  */

  private class UndoAction extends AbstractAction {

      public void actionPerformed( ActionEvent evt ) {
         undoManager_.undo();
    	 refreshUndoRedo();
      }
  }

  /**
  * inner class that defines the redo action
  *
  */


  private class RedoAction extends AbstractAction {

     public void actionPerformed(ActionEvent evt ) {
     	undoManager_.redo();
     	refreshUndoRedo();
     }
  }

  /**
  * An undo/redo adpater. The adpater is notified when
  * an undo edit occur(e.g. add or remove from the list)
  * The adptor extract the edit from the event, add it
  * to the UndoManager, and refresh the GUI
  */

  private class UndoAdapter implements UndoableEditListener {
     public void undoableEditHappened (UndoableEditEvent evt) {
     	UndoableEdit edit = evt.getEdit();
     	undoManager_.addEdit( edit );
     	refreshUndoRedo();
     }
  }

  /**
  * The list selection adpater change the remove button state
  * according to the selection of the list
  */
  private class ListSelectionAdapter implements ListSelectionListener {
     public void valueChanged (ListSelectionEvent evt) {
     	if ( evt.getLastIndex() >= evt.getFirstIndex()) {
              removeElementBtn_.setEnabled(true);
        }
     }
  }


}











