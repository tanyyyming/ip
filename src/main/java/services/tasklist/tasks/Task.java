package services.tasklist.tasks;

import services.tagging.Tag;
import services.tagging.Taggable;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Represents a task.
 */
public abstract class Task implements Taggable {
    /** The description of the task. */
    protected String description;
    /** The status of task completion. */
    protected boolean isDone;
    /** The checkbox to indicate the status of task completion. */
    protected String checkBox;
    protected HashSet<Tag> tags;

    /**
     * Constructor for Task.
     * The task is not done by default.
     *
     * @param description the content of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.checkBox = "[ ]";
        this.tags = new HashSet<>();
    }

    public void setDone() {
        this.isDone = true;
        this.checkBox = "[X]";
    }

    public void setUndone() {
        this.isDone = false;
        this.checkBox = "[ ]";
    }

    @Override
    public void addTags(String[] tagNames) {
        for (String tagName : tagNames) {
            addTag(tagName);
        }
    }

    private void addTag(String tagName) {
        Tag newTag;
        if (Tag.getTag(tagName) != null) {
            newTag = Tag.getTag(tagName);
        } else {
            newTag = new Tag(tagName);
        }

        tags.add(newTag);
    }

    @Override
    public void deleteTags(String[] tagNames) {
        for (String tagName : tagNames) {
            deleteTag(tagName);
        }
    }

    private void deleteTag(String tagName) {
        Tag tagToDelete = Tag.getTag(tagName);
        if (tagToDelete == null) {
            return;
        }

        tags.remove(tagToDelete);
    }

    public String showAllTags() {
        return tags.stream().map(Tag::toString).collect(Collectors.joining(" "));
    }

    /**
     * Encodes the task into a string that can be saved to a data file.
     *
     * @return the encoded string.
     */
    public abstract String encode();

    @Override
    public String toString() {
        return checkBox + " " + this.description + " " + showAllTags();
    }
}
