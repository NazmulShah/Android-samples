<<<<<<< HEAD
package com.neevtech.imageprocessing.paintutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.neevtech.imageprocessing.PaintActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.Toast;

public class CommandManager {
	public List<DrawingPath> currentStack;
	private List<DrawingPath> redoStack;
	private List<StringPath> stringCurrentStack;
	private List<StringPath> stringRedoStack;
	private Context context;

	public CommandManager(Context context) {
		this.context = context;
		currentStack = Collections
				.synchronizedList(new ArrayList<DrawingPath>());
		redoStack = Collections.synchronizedList(new ArrayList<DrawingPath>());
		stringCurrentStack = Collections
				.synchronizedList(new ArrayList<StringPath>());
		stringRedoStack = Collections
				.synchronizedList(new ArrayList<StringPath>());
	}

	public void addCommand(DrawingPath command) {
		redoStack.clear();
		currentStack.add(command);
	}

	public void clearPath(DrawingPath command) {
		// command.
	}

	public void addCommand(StringPath text) {
		stringRedoStack.clear();
		stringCurrentStack.add(text);
	}

	public void undo() {
		final int length = currentStackLength();

		if (length > 0) {
			final DrawingPath undoCommand = currentStack.get(length - 1);
			currentStack.remove(length - 1);
			undoCommand.undo();
			redoStack.add(undoCommand);
			PaintActivity.isSaved = false;
		} else{
			Toast.makeText(context, "Nothing more strokes to undo",
					Toast.LENGTH_LONG).show();
			PaintActivity.isSaved = true;
			}
	}

	public void undo1() {
		final int length1 = currentStackLength1();
		if (length1 > 0) {
			final StringPath undoCommand1 = stringCurrentStack.get(length1 - 1);
			stringCurrentStack.remove(length1 - 1);
			undoCommand1.undo();
			stringRedoStack.add(undoCommand1);
		}
	}

	public int currentStackLength() {
		final int length = currentStack.toArray().length;
		return length;
	}

	public int currentStackLength1() {
		final int length1 = stringCurrentStack.toArray().length;
		return length1;
	}

	public void executeAll(Canvas canvas, Handler doneHandler) {
		if (currentStack != null) {
			synchronized (currentStack) {
				final Iterator i = currentStack.iterator();

				while (i.hasNext()) {
					final DrawingPath drawingPath = (DrawingPath) i.next();
					drawingPath.draw(canvas);
					//doneHandler.sendEmptyMessage(1);
				}
			}
		}
		if (stringCurrentStack != null) {
			synchronized (stringCurrentStack) {
				final Iterator i = stringCurrentStack.iterator();

				while (i.hasNext()) {
					final StringPath stringPath = (StringPath) i.next();
					stringPath.draw(canvas);
					// doneHandler.sendEmptyMessage(1);
				}
			}
		}
	}

	public boolean hasMoreRedo() {
		return redoStack.toArray().length > 0;
	}

	public boolean hasMoreUndo() {
		return currentStack.toArray().length > 0;
	}

	public boolean hasMoreUndo1() {
		return stringCurrentStack.toArray().length > 0;
	}

	public void redo() {
		final int length = redoStack.toArray().length;
		if (length > 0) {
			final DrawingPath redoCommand = redoStack.get(length - 1);
			redoStack.remove(length - 1);
			currentStack.add(redoCommand);
			PaintActivity.isSaved = false;
		} else{
			Toast.makeText(context, "Nothing more strokes to redo",
					Toast.LENGTH_LONG).show();
		PaintActivity.isSaved = true;
		}
	}
}
=======
package com.neevtech.imageprocessing.paintutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.neevtech.imageprocessing.PaintActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.Toast;

public class CommandManager {
	public List<DrawingPath> currentStack;
	private List<DrawingPath> redoStack;
	private List<StringPath> stringCurrentStack;
	private List<StringPath> stringRedoStack;
	private Context context;

	public CommandManager(Context context) {
		this.context = context;
		currentStack = Collections
				.synchronizedList(new ArrayList<DrawingPath>());
		redoStack = Collections.synchronizedList(new ArrayList<DrawingPath>());
		stringCurrentStack = Collections
				.synchronizedList(new ArrayList<StringPath>());
		stringRedoStack = Collections
				.synchronizedList(new ArrayList<StringPath>());
	}

	public void addCommand(DrawingPath command) {
		redoStack.clear();
		currentStack.add(command);
	}

	public void clearPath(DrawingPath command) {
		// command.
	}

	public void addCommand(StringPath text) {
		stringRedoStack.clear();
		stringCurrentStack.add(text);
	}

	public void undo() {
		final int length = currentStackLength();

		if (length > 0) {
			final DrawingPath undoCommand = currentStack.get(length - 1);
			currentStack.remove(length - 1);
			undoCommand.undo();
			redoStack.add(undoCommand);
			PaintActivity.isSaved = false;
		} else{
			Toast.makeText(context, "Nothing more strokes to undo",
					Toast.LENGTH_LONG).show();
			PaintActivity.isSaved = true;
			}
	}

	public void undo1() {
		final int length1 = currentStackLength1();
		if (length1 > 0) {
			final StringPath undoCommand1 = stringCurrentStack.get(length1 - 1);
			stringCurrentStack.remove(length1 - 1);
			undoCommand1.undo();
			stringRedoStack.add(undoCommand1);
		}
	}

	public int currentStackLength() {
		final int length = currentStack.toArray().length;
		return length;
	}

	public int currentStackLength1() {
		final int length1 = stringCurrentStack.toArray().length;
		return length1;
	}

	public void executeAll(Canvas canvas, Handler doneHandler) {
		if (currentStack != null) {
			synchronized (currentStack) {
				final Iterator i = currentStack.iterator();

				while (i.hasNext()) {
					final DrawingPath drawingPath = (DrawingPath) i.next();
					drawingPath.draw(canvas);
					//doneHandler.sendEmptyMessage(1);
				}
			}
		}
		if (stringCurrentStack != null) {
			synchronized (stringCurrentStack) {
				final Iterator i = stringCurrentStack.iterator();

				while (i.hasNext()) {
					final StringPath stringPath = (StringPath) i.next();
					stringPath.draw(canvas);
					// doneHandler.sendEmptyMessage(1);
				}
			}
		}
	}

	public boolean hasMoreRedo() {
		return redoStack.toArray().length > 0;
	}

	public boolean hasMoreUndo() {
		return currentStack.toArray().length > 0;
	}

	public boolean hasMoreUndo1() {
		return stringCurrentStack.toArray().length > 0;
	}

	public void redo() {
		final int length = redoStack.toArray().length;
		if (length > 0) {
			final DrawingPath redoCommand = redoStack.get(length - 1);
			redoStack.remove(length - 1);
			currentStack.add(redoCommand);
			PaintActivity.isSaved = false;
		} else{
			Toast.makeText(context, "Nothing more strokes to redo",
					Toast.LENGTH_LONG).show();
		PaintActivity.isSaved = true;
		}
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
