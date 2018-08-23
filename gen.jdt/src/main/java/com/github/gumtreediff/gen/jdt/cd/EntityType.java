/*
 * Copyright 2009 University of Zurich, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.gumtreediff.gen.jdt.cd;

/**
 * All types for source code entities that are used by ChangeDistiller to build up the AST (abstract syntax tree). Most
 * are taken from {@link org.eclipse.jdt.core.dom.ASTNode}.
 * 
 * @author zubi
 */
public enum EntityType {
    ANNOTATION_TYPE_DECLARATION(81, true),
    ANNOTATION_TYPE_MEMBER_DECLARATION(82, true),
    ANONYMOUS_CLASS_DECLARATION(1, true),
    ARGUMENTS(100, false),
    ARRAY_ACCESS(2, true),
    ARRAY_CREATION(3, true),
    ARRAY_DIMENSION(85, true),
    ARRAY_INITIALIZER(4, true),
    ARRAY_TYPE(5, true),
    ASSERT_STATEMENT(6, true),
    ASSIGNMENT(7, true),
    ATTRIBUTE(-1, false),
    BLOCK(8, false),
    BLOCK_COMMENT(64, true),
    BODY(-1, false),
    BODY_DECLARATIONS(-1, false),
    BOOLEAN_LITERAL(9, true),
    BREAK_STATEMENT(10, true),
    CAST_EXPRESSION(11, true),
    CATCH_CLAUSE(12, true),
    CATCH_CLAUSES(-1, false),
    CHARACTER_LITERAL(13, true),
    CLASS(-1, false),
    CLASS_INSTANCE_CREATION(14, true),
    COMPILATION_UNIT(15, true),
    CONDITIONAL_FOR_EXPRESSION(106, true),
    CONDITIONAL_EXPRESSION(16, true),
    CONSTRUCTOR_INVOCATION(17, true),
    CONTINUE_STATEMENT(18, true),
    DO_STATEMENT(19, true),
    ELSE_STATEMENT(101, true),
    EMPTY_STATEMENT(20, true),
    ENHANCED_FOR_STATEMENT(70, true),
    ENUM_CONSTANT_DECLARATION(72, true),
    ENUM_CONSTANTS(-1, false),
    ENUM_DECLARATION(71, true),
    EXPRESSION_STATEMENT(21, true),
    EXTENDED_OPERANDS(-1, false),
    FIELD_ACCESS(22, true),
    FIELD_DECLARATION(23, true),
    FINALLY(-1, false),
    FOR_STATEMENT(24, true),
    FRAGMENTS(-1, false),
    IF_STATEMENT(25, true),
    IMPORT_DECLARATION(26, true),
    INFIX_EXPRESSION(27, true),
    INITIALIZER(28, true),
    INITIALIZERS(103, false),
    INSTANCEOF_EXPRESSION(62, true),
    JAVADOC(29, true),
    LABELED_STATEMENT(30, true),
    LINE_COMMENT(63, true),
    MARKER_ANNOTATION(78, true),
    MEMBER_REF(67, true),
    MEMBER_VALUE_PAIR(80, true),
    METHOD(-1, false),
    METHOD_DECLARATION(31, true),
    METHOD_INVOCATION(32, true),
    METHOD_REF(68, true),
    METHOD_REF_PARAMETER(69, true),
    MODIFIER(83, true),
    MODIFIERS(-1, false),
    NORMAL_ANNOTATION(77, true),
    NULL_LITERAL(33, true),
    NUMBER_LITERAL(34, true),
    PACKAGE_DECLARATION(35, true),
    PARAMETERIZED_TYPE(74, true),
    PARAMETERS(-1, false),
    PARENTHESIZED_EXPRESSION(36, true),
    POSTFIX_EXPRESSION(37, true),
    PREFIX_EXPRESSION(38, true),
    PRIMITIVE_TYPE(39, true),
    QUALIFIED_NAME(40, true),
    QUALIFIED_TYPE(75, true),
    RETURN_STATEMENT(41, true),
    ROOT_NODE(-1, true),
    SIMPLE_NAME(42, true),
    SIMPLE_TYPE(43, true),
    SINGLE_MEMBER_ANNOTATION(79, true),
    SINGLE_VARIABLE_DECLARATION(44, true),
    STRING_LITERAL(45, true),
    SUPER_CLASS_TYPE(105, true),
    SUPER_CONSTRUCTOR_INVOCATION(46, true),
    SUPER_FIELD_ACCESS(47, true),
    SUPER_INTERFACE_TYPES(104, false),
    SUPER_METHOD_INVOCATION(48, true),
    SWITCH_CASE(49, true),
    SWITCH_STATEMENT(50, true),
    SYNCHRONIZED_STATEMENT(51, true),
    TAG_ELEMENT(65, true),
    TEXT_ELEMENT(66, true),
    THEN_STATEMENT(-1, true),
    THIS_EXPRESSION(52, true),
    THROW(-1, false),
    THROW_STATEMENT(53, true),
    TRY_STATEMENT(54, true),
    TYPE_ARGUMENTS(107, false),
    TYPE_DECLARATION(55, true),
    TYPE_DECLARATION_STATEMENT(56, true),
    TYPE_LITERAL(57, true),
    TYPE_PARAMETER(73, true),
    UPDATERS(102, false),
    VARIABLE_DECLARATION_EXPRESSION(58, true),
    VARIABLE_DECLARATION_FRAGMENT(59, true),
    VARIABLE_DECLARATION_STATEMENT(60, true),
    WHILE_STATEMENT(61, true),
    WILDCARD_TYPE(76, true);

    private final boolean fIsValidChange;
    private final int type;

    private EntityType(int jdtType, boolean isValidChange) {
        type = jdtType;
        fIsValidChange = isValidChange;
    }

    /**
     * Returns number of defined entity types.
     * 
     * @return number of entity types.
     */
    public static int getNumberOfEntityTypes() {
        return values().length;
    }

    /**
     * Returns whether changes occurred on this source code entity type are extracted by ChangeDistiller or not (e.g.
     * changes in the <code>finally</code> clause are ignored).
     * 
     * @return <code>true</code> if changes on this entity type are considered and extracted, <code>false</code>
     *         otherwise.
     */
    public boolean isValidChange() {
        return fIsValidChange;
    }

    /**
     * Returns whether the given entity type is a type of a type declaration or not.
     * 
     * @param type
     *            to analyze
     * @return <code>true</code> if given entity type is a type, <code>false</code> otherwise.
     */
    public static boolean isType(EntityType type) {
        switch (type) {
            case ARRAY_TYPE:
            case PARAMETERIZED_TYPE:
            case PRIMITIVE_TYPE:
            case QUALIFIED_TYPE:
            case SIMPLE_TYPE:
            case WILDCARD_TYPE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the given entity type is a statement or not.
     * 
     * @param type
     *            to analyze
     * @return <code>true</code> if given entity type is a statement, <code>false</code> otherwise.
     */
    public static boolean isAtStatementLevel(EntityType type) {
        switch (type) {
            case ASSERT_STATEMENT:
            case ASSIGNMENT:
            case BREAK_STATEMENT:
            case CATCH_CLAUSE:
            case CLASS_INSTANCE_CREATION:
            case CONSTRUCTOR_INVOCATION:
            case CONTINUE_STATEMENT:
            case DO_STATEMENT:
            case FINALLY:
            case FOR_STATEMENT:
            case IF_STATEMENT:
            case LABELED_STATEMENT:
            case METHOD_INVOCATION:
            case RETURN_STATEMENT:
            case SUPER_CONSTRUCTOR_INVOCATION:
            case SUPER_METHOD_INVOCATION:
            case SWITCH_CASE:
            case SWITCH_STATEMENT:
            case SYNCHRONIZED_STATEMENT:
            case THROW_STATEMENT:
            case TRY_STATEMENT:
            case VARIABLE_DECLARATION_STATEMENT:
            case WHILE_STATEMENT:
            case ENHANCED_FOR_STATEMENT:
                return true;
            default:
                return false;
        }
    }


    public String getJdtCompliantName() {
        StringBuilder compliantName = new StringBuilder();
        String []part = name().split("_");
        for (int i = 0; i < part.length; i++) {
            compliantName.append(part[i].substring(0, 1).toUpperCase())
                         .append(part[i].substring(1).toLowerCase());
        }
        return compliantName.toString();
    }

    public int getType() {
        return type;
    }
}
