//This java file contains an extra credit method called solve.
//It functions.
//But it doesn't pass style check because the programmer wrote an
//extremely nested method.

//In memory of this sad moment, made a special copy for it 
package calculator.ast;

import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import calculator.gui.ImageDrawer;

/**
 * All of the public static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulators {
    /**
     * Checks to make sure that the given node is an operation AstNode with the expected
     * name and number of children. Throws an EvaluationError otherwise.
     */
    private static void assertNodeMatches(AstNode node, String expectedName, int expectedNumChildren) {
        if (!node.isOperation()
                && !node.getName().equals(expectedName)
                && node.getChildren().size() != expectedNumChildren) {
            throw new EvaluationError("Node is not valid " + expectedName + " node.");
        }
    }

    /**
     * Accepts an 'toDouble(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'toDouble'.
     * - The 'node' parameter has exactly one child: the AstNode to convert into a double.
     *
     * Postconditions:
     *
     * - Returns a number AstNode containing the computed double.
     *
     * For example, if this method receives the AstNode corresponding to
     * 'toDouble(3 + 4)', this method should return the AstNode corresponding
     * to '7'.
     * 
     * This method is required to handle the following binary operations
     *      +, -, *, /, ^
     *  (addition, subtraction, multiplication, division, and exponentiation, respectively) 
     * and the following unary operations
     *      negate, sin, cos
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if any of the expressions uses an unknown operation.
     */
    public static AstNode handleToDouble(Environment env, AstNode node) {
        // To help you get started, we've implemented this method for you.
        // You should fill in the locations specified by "your code here"
        // in the 'toDoubleHelper' method.
        //
        // If you're not sure why we have a public method calling a private
        // recursive helper method, review your notes from CSE 143 (or the
        // equivalent class you took) about the 'public-private pair' pattern.

        assertNodeMatches(node, "toDouble", 1);
        AstNode exprToConvert = node.getChildren().get(0);
        return new AstNode(toDoubleHelper(env.getVariables(), exprToConvert));
    }

    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases. 
        if (node.isNumber()) {
            return node.getNumericValue();
        } else if (node.isVariable()) {
            String name = node.getName();
            if (variables.containsKey(name)) {
                return toDoubleHelper(variables, variables.get(name)); 
            } else {
                throw new EvaluationError("Unknown variable.");
            }
        } else {
            // You may assume the expression node has the correct number of children.
            // If you wish to make your code more robust, you can also use the provided
            // "assertNodeMatches" method to verify the input is valid.
            String name = node.getName();
            IList<AstNode> children = node.getChildren();
            if (name.equals("+")) {
                return toDoubleHelper(variables, children.get(0)) + toDoubleHelper(variables, children.get(1));
            } else if (name.equals("-")) {
                return toDoubleHelper(variables, children.get(0)) - toDoubleHelper(variables, children.get(1));
            } else if (name.equals("*")) {
               return toDoubleHelper(variables, children.get(0)) * toDoubleHelper(variables, children.get(1));
            } else if (name.equals("/")) {
                return toDoubleHelper(variables, children.get(0)) / toDoubleHelper(variables, children.get(1));
            } else if (name.equals("^")) {
                return Math.pow(toDoubleHelper(variables, children.get(0)), 
                        toDoubleHelper(variables, children.get(1)));
            } else if (name.equals("sin")) {
                return Math.sin(toDoubleHelper(variables, children.get(0)));
            } else if (name.equals("cos")) {
                return Math.cos(toDoubleHelper(variables, children.get(0)));
            } else if (name.equals("negate")) {
                return (-1) * toDoubleHelper(variables, children.get(0));
            } else {
                throw new EvaluationError("Unknown operation."); 
            }
        }
        
}


    /**
     * Accepts a 'simplify(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'simplify'.
     * - The 'node' parameter has exactly one child: the AstNode to simplify
     *
     * Postconditions:
     *
     * - Returns an AstNode containing the simplified inner parameter.
     *
     * For example, if we received the AstNode corresponding to the expression
     * "simplify(3 + 4)", you would return the AstNode corresponding to the
     * number "7".
     *
     * Note: there are many possible simplifications we could implement here,
     * but you are only required to implement a single one: constant folding.
     *
     * That is, whenever you see expressions of the form "NUM + NUM", or
     * "NUM - NUM", or "NUM * NUM", simplify them.
     */
    public static AstNode handleSimplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        // Hint 1: Your code will likely be structured roughly similarly
        //         to your "handleToDouble" method
        // Hint 2: When you're implementing constant folding, you may want
        //         to call your "handleToDouble" method in some way
        // Hint 3: When implementing your private pair, think carefully about
        //         when you should recurse. Do you recurse after simplifying
        //         the current level? Or before?

        assertNodeMatches(node, "simplify", 1);
        AstNode exprToConvert = node.getChildren().get(0);
        return simplifyHelper(env, exprToConvert);
    }
    
    private static AstNode simplifyHelper(Environment env, AstNode node) {
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            String name = node.getName();
            IDictionary<String, AstNode> variables = env.getVariables();
            if (variables.containsKey(name)) {
                node = variables.get(name);
                return simplifyHelper(env, node);
            } else {
                return node;
            }
         } else {
           String name = node.getName();
           IList<AstNode> children = node.getChildren();
           if (children.size() == 2) {
               if ((name.equals("+") || name.equals("-")  || name.equals("*")) &&
                 children.get(0).isNumber() && children.get(1).isNumber()){ 
                   IList<AstNode> passNode = new DoubleLinkedList<AstNode>();
                   passNode.add(node);
                   return handleToDouble(env, new AstNode("toDouble", passNode));
                 } else { //simplify two branches and return the simplified node
                    IList<AstNode> simplifiedNode = new DoubleLinkedList<AstNode>();
                    simplifiedNode.add(simplifyHelper(env, children.get(0)));
                    simplifiedNode.add(simplifyHelper(env, children.get(1)));
                    return new AstNode(name, simplifiedNode);
                    // simplified node remains the operation name 
                    //with two children being simplified                                           
                 }
               
           } else { //simplify two branches and return the simplified node
               IList<AstNode> simplifiedNode = new DoubleLinkedList<AstNode>();
               simplifiedNode.add(simplifyHelper(env, children.get(0)));
               return new AstNode(name, simplifiedNode);
           }
         }


    }
    
    /**
     * Accepts an Environment variable and a 'plot(exprToPlot, var, varMin, varMax, step)'
     * AstNode and generates the corresponding plot on the ImageDrawer attached to the
     * environment. Returns some arbitrary AstNode.
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This method will receive the AstNode corresponding to 'plot(3 * x, x, 2, 5, 0.5)'.
     * Your 'handlePlot' method is then responsible for plotting the equation
     * "3 * x", varying "x" from 2 to 5 in increments of 0.5.
     *
     * In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'var' was already defined
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode plot(Environment env, AstNode node) {
        assertNodeMatches(node, "plot", 5);
        IList<AstNode> children = new DoubleLinkedList<AstNode>();
        children = node.getChildren();
        IDictionary<String, AstNode> variables = env.getVariables(); 
        //five children of a plot node
        AstNode function = children.get(0);
        AstNode vName = children.get(1);
        AstNode vMin = children.get(2); 
        AstNode vMax = children.get(3);
        AstNode increment = children.get(4);
        //change the vMin, vMax, increment type from AstNode to Double
        //To ask:
        //if min or max is a negative value, AstNode type is actually OPERATION 
        //HandleToDouble will check for the undefined variables other than the "x" variable,
        // and return the EvaluationError("Unknown variables")
        IList<AstNode> step = new DoubleLinkedList<AstNode>();
        IList<AstNode> varMin = new DoubleLinkedList<AstNode>();
        IList<AstNode> varMax = new DoubleLinkedList<AstNode>();
        IList<AstNode> fuc = new DoubleLinkedList<AstNode>();
        fuc.add(function);
        step.add(increment);
        varMin.add(vMin);
        varMax.add(vMax);
        double inc = handleToDouble(env, new AstNode("toDouble", step)).getNumericValue();
        double min = handleToDouble(env, new AstNode("toDouble", varMin)).getNumericValue();
        double max = handleToDouble(env, new AstNode("toDouble", varMax)).getNumericValue();
        
        if (min > max) {
            throw new EvaluationError("varMin > varMax");
        } else if (variables.containsKey(vName.getName())) {
            throw new EvaluationError("Given variable is already defined");
        } else if (inc <= 0) {
            throw new EvaluationError("Step can't be zero or negative");
        } else {
            //get the points coordinate
            IList<Double> xValues = new DoubleLinkedList<Double>();
            IList<Double> yValues = new DoubleLinkedList<Double>(); 
            for (double i = min; i <= max; i += inc) {
                xValues.add(i);
                variables.put(vName.getName(), new AstNode(i));
                double yValue = handleToDouble(env, new AstNode("toDouble", fuc)).getNumericValue();
                yValues.add(yValue);
            }
            //plot the scatter points
            ImageDrawer image = env.getImageDrawer();
            image.drawScatterPlot("Plot", vName.getName(), "Output", xValues, yValues);
            //plot cleans up variable
            variables.remove(vName.getName());
            
            // Note: every single function we add MUST return an
            // AST node that your "simplify" function is capable of handling.
            // However, your "simplify" function doesn't really know what to do
            // with "plot" functions (and what is the "plot" function supposed to
            // evaluate to anyways?) so we'll settle for just returning an
            // arbitrary number.
            //
            // When working on this method, you should uncomment the following line:
            //
            return new AstNode(1);
        }
    }
    
    //    public static AstNode handleSolve(Environment env, AstNode node) {
//        assertNodeMatches(node, "solve", 1);
//        IList<AstNode> children = node.getChildren();
//        if (children.size() == 2 && children.get(1).isVariable()) {
//            AstNode variable = children.get(1);          
//            if (env.getVariables().containsKey(variable.getName())) {
//                throw new EvaluationError("Variable already defined");
//            } else {
//                AstNode result = handleEqual(env, children.get(0), variable);
//                return result;
//            }
//        } else {
//            throw new EvaluationError("Invalid Input1");
//        }   
//    }
    
//    private static AstNode handleEqual(Environment env, AstNode node1, AstNode node2) {
//        assertNodeMatches(node1, "equal", 1);
//        IList<AstNode> children = node1.getChildren();
//        String name2 = node2.getName();
//        if (children.size() == 2) {
//            AstNode left = children.get(0); //left of the equal sign 
//            AstNode right = children.get(1); //right of the equal sign
//            if ((left.isOperation() && right.isNumber()) || (left.isNumber() && right.isOperation())) {
//                if (left.isOperation()) {
//                    IList<AstNode> formulaList = new DoubleLinkedList<AstNode>();
//                    formulaList.add(left);
//                    IList<AstNode> computeExpression = new DoubleLinkedList<AstNode>();
//                    IList<AstNode> values = new DoubleLinkedList<AstNode>();
//                    IList<AstNode> childrenUnknown = left.getChildren();
//                    AstNode pass = null;
//                    String unknownName;
//                    values.add(right);
//                    if (childrenUnknown.get(0).isNumber() && childrenUnknown.get(1).isVariable()) {
//                        unknownName = childrenUnknown.get(1).getName();
//                       if (!unknownName.equals(name2)) {
//                           throw new EvaluationError("Variable doesn't match.");
//                       } else {
//                           values.add(childrenUnknown.get(0)); 
//                       }
//                    } else if (childrenUnknown.get(0).isVariable() && childrenUnknown.get(1).isNumber()){
//                        unknownName = childrenUnknown.get(0).getName();
//                        if (unknownName.equals(name2)) {
//                            throw new EvaluationError("Variable doesn't match.");
//                        } else {
//                            values.add(childrenUnknown.get(1)); 
//                        }  
//                    } else {
//                        throw new EvaluationError("Invalid Input");
//                    }
//                    if (left.getName().equals("+")) {
//                        pass = new AstNode("-", values);
//                    } else if (left.getName().equals("-")) {
//                        pass = new AstNode("+", values);
//                    } else if (left.getName().equals("*")) {
//                        pass = new AstNode("/", values);
//                    } else if (left.getName().equals("/")) {
//                        pass = new AstNode("*", values);
//                    } else {
//                        throw new EvaluationError("Unsupported operator.");
//                    }
//                    computeExpression.add(pass);
//                    return handleToDouble(env, new AstNode("toDouble", computeExpression));
//                } else {
//                    IList<AstNode> formulaList = new DoubleLinkedList<AstNode>();
//                    formulaList.add(right);
//                    right = handleSimplify(env, new AstNode("simplify", formulaList));
//                    IList<AstNode> childrenUnknown = right.getChildren();
//                    IList<AstNode> computeExpression = new DoubleLinkedList<AstNode>();
//                    IList<AstNode> values = new DoubleLinkedList<AstNode>();
//                    AstNode pass = null;
//                    String unknownName;
//                    values.add(left);
//                    if (childrenUnknown.get(0).isNumber() && childrenUnknown.get(1).isVariable()) {
//                        unknownName = childrenUnknown.get(1).getName();
//                        if (!unknownName.equals(name2)) {
//                            throw new EvaluationError("Variable doesn't match.");
//                        } else {
//                            values.add(childrenUnknown.get(0)); 
//                        }
//                    } else if (childrenUnknown.get(0).isVariable() && childrenUnknown.get(1).isNumber()){
//                        unknownName = childrenUnknown.get(0).getName();
//                        if (!unknownName.equals(name2)) {
//                            throw new EvaluationError("Variables don't match.");
//                        } else {
//                            values.add(childrenUnknown.get(1)); 
//                        }
//                    } else {
//                        throw new EvaluationError("Invalid Input");
//                    }
//                    if (right.getName().equals("+")) {
//                        pass = new AstNode("-", values);
//                    } else if (right.getName().equals("-")) {
//                        pass = new AstNode("+", values);
//                    } else if (right.getName().equals("*")) {
//                        pass = new AstNode("/", values);
//                    } else if (right.getName().equals("/")) {
//                        pass = new AstNode("*", values);
//                    } else {
//                        throw new EvaluationError("Unsupported operator.");
//                    }
//                    computeExpression.add(pass);
//                    return handleToDouble(env, new AstNode("toDouble", computeExpression));
//                }
//            } else {
//                throw new EvaluationError("Invalid Input");
//              }
//            
//              } else {
//                  throw new EvaluationError("Invalid Input");
//              }

        //}



}
