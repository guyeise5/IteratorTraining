# Iterator Training
Expand java `Iterator` based on Scala API.

### Background
The interface `Iterator<T>` in java has 2 basic methods `next()` and `hasNext()`
- `next()` fetches the next element from the iterator throws `NoSuchElementException` if the iterator has none left.
- `hasNext()` checks if the iterator can provide more elements in other words, if `next()` is safe to execute.

In this exercise you are going to use those methods to implement much more complex logic.

### Before you begin
`mvn dependency:resolve`

---

### Assignment 
The interface `RichIterator<A>` extends the basic interface `Iterator<A>`
that was described before. It is located at `src/main/java/iterator/RichIterator.java`.
This interface has many methods that are implemented with default
implementation `throw new NotImplementedExpcetion();`. Your job it to implement those
methods. 

### Guideline
The methods in `RichIterator<A>` are divided to three levels. `Easy`, `Hard`, `Very Hard`.
Your work process should follow the following instructions:
* Implement **ALL** the methods in the Easy category
* Execute the tests `mvn test -Dtest="testing.levels.easy.**"` 
* Fix all the functions which fails (do it without reading/debugging the tests!)
* Repeat for `Hard` and `Very Hard` (notice to execute the tests according to the level)

---

### Extra
After finishing all the levels try to solve the Extra cases.
The extra cases are functions that reminds more day-to-day
code implementation. All those functions should be implemented
with the functions that you implemented before for better understanding.

In class `Extra` which located at `src/main/java/iterator/Extra`
provides several functions.
Perform the guideline with them as well.