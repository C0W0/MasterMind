/*=============================================================================
Code Breaker
Terry Zha and Jonathan Xie
October 4, 2020
Java 13.0.2
This interface is used to store a procedure for mouse click. It usually
is created with the form of a method reference or lambda expression
===============================================================================
*/

package ui;

public interface ClickListener {

    /**onClick method
     * Procedures are stored in this procedural method when a
     * ClickListener is created
     */
    public void onClick();

}
