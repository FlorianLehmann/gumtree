/*
 * This file is part of GumTree.
 *
 * GumTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GumTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GumTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2011-2015 Jean-Rémy Falleri <jr.falleri@gmail.com>
 * Copyright 2011-2015 Floréal Morandat <florealm@gmail.com> *
 */


package com.github.gumtreediff.gen.jdt;


import com.github.gumtreediff.gen.jdt.cd.EntityType;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.internal.compiler.ast.Argument;

public class JdtVisitor extends AbstractJdtVisitor {
    public JdtVisitor() {
        super();
    }

    @Override
    public void preVisit(ASTNode n) {
        pushNode(n, getLabel(n));
    }

    @Override
    public boolean visit(IfStatement node) {
        node.getExpression().accept(this);
        node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) {
            pushFakeNode(EntityType.ELSE_STATEMENT, "", -1, -1);
            node.getElseStatement().accept(this);
            popNode();
        }
        return false;
    }

    @Override
    public boolean visit(MethodInvocation node) {

        if (node.getExpression() != null) {
            final String expression = node.getExpression().toString();
            int startPosition = -1;
            pushFakeNode(EntityType.SIMPLE_NAME, expression, startPosition, expression.length());
            popNode();
        }

        if (!node.typeArguments().isEmpty()) {
            int startPosition = -1;
            pushFakeNode(EntityType.TYPE_ARGUMENTS, "", startPosition, "".length());
            for (Object o : node.typeArguments()) {
                final String expression = ((Type) o).toString();
                startPosition = -1;
                pushFakeNode(EntityType.SIMPLE_NAME, expression, startPosition, expression.length());
                popNode();
            }
            popNode();
        }

        final String methodName = node.getName().toString();
        int startPosition = -1;
        pushFakeNode(EntityType.SIMPLE_NAME, methodName, startPosition, methodName.length());
        popNode();

        if (!node.arguments().isEmpty()) {
            startPosition = -1;
            pushFakeNode(EntityType.ARGUMENTS, "", startPosition, "".length());
            for (Object o : node.arguments()) {
                final String expression = ((Expression) o).toString();
                startPosition = -1;
                pushFakeNode(EntityType.SIMPLE_NAME, expression, startPosition, expression.length());
                popNode();
            }
            popNode();
        }

        return false;
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        if (node.isStatic()) {
            final String statik = "static";
            int startPosition = node.getRoot().toString().indexOf(statik);
            pushFakeNode(EntityType.MODIFIER, statik, startPosition, statik.length());
            popNode();
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(TypeDeclaration node) {

        for (Object o : node.modifiers()) {
            if (o instanceof Modifier) {
                ((Modifier) o).accept(this);
            } else {
                ((Annotation) o).accept(this);
            }
        }

        pushFakeNode(EntityType.SIMPLE_NAME, node.getName().toString(), -1, -1);
        popNode();

        if (node.getSuperclassType() != null) {
            pushFakeNode(EntityType.SUPER_CLASS_TYPE, "", -1, -1);
            node.getSuperclassType().accept(this);
            popNode();
        }

        if (!node.superInterfaceTypes().isEmpty()) {
            pushFakeNode(EntityType.SUPER_INTERFACE_TYPES, "", -1, -1);
            for (Object o : node.superInterfaceTypes()) {
                ((Type) o).accept(this);
            }
            popNode();
        }

        for (Object o : node.bodyDeclarations()) {
            ((BodyDeclaration) o).accept(this);
        }


        return false;
    }

    @Override
    public boolean visit(ForStatement node) {

        if (!node.initializers().isEmpty()) {
            pushFakeNode(EntityType.INITIALIZERS, "", -1, -1);
            for (Object o : node.initializers()) {
                ((Expression) o).accept(this);
            }
            popNode();
        }

        if (node.getExpression()!=null) {
            pushFakeNode(EntityType.CONDITIONAL_FOR_EXPRESSION, "", -1, -1);
            node.getExpression().accept(this);
            popNode();
        }

        if (!node.updaters().isEmpty()) {
            pushFakeNode(EntityType.UPDATERS, "", -1, -1);
            for (Object o : node.updaters()) {
                ((Expression) o).accept(this);
            }
            popNode();
        }

        node.getBody().accept(this);
        return false;
    }

    @Override
    public boolean visit(SuperMethodInvocation node) {

        if (!node.typeArguments().isEmpty()) {
            int startPosition = -1;
            pushFakeNode(EntityType.TYPE_ARGUMENTS, "", startPosition, "".length());
            for (Object o : node.typeArguments()) {
                final String expression = ((Type) o).toString();
                startPosition = -1;
                pushFakeNode(EntityType.SIMPLE_NAME, expression, startPosition, expression.length());
                popNode();
            }
            popNode();
        }

        final String methodName = node.getName().toString();
        int startPosition = -1;
        pushFakeNode(EntityType.SIMPLE_NAME, methodName, startPosition, methodName.length());
        popNode();

        if (!node.arguments().isEmpty()) {
            startPosition = -1;
            pushFakeNode(EntityType.ARGUMENTS, "", startPosition, "".length());
            for (Object o : node.arguments()) {
                final String expression = ((Expression) o).toString();
                startPosition = -1;
                pushFakeNode(EntityType.SIMPLE_NAME, expression, startPosition, expression.length());
                popNode();
            }
            popNode();
        }


        return false;
    }

    protected String getLabel(ASTNode n) {
        if (n instanceof Name) return ((Name) n).getFullyQualifiedName();
        if (n instanceof Type) return n.toString();
        if (n instanceof Modifier) return n.toString();
        if (n instanceof StringLiteral) return ((StringLiteral) n).getEscapedValue();
        if (n instanceof NumberLiteral) return ((NumberLiteral) n).getToken();
        if (n instanceof CharacterLiteral) return ((CharacterLiteral) n).getEscapedValue();
        if (n instanceof BooleanLiteral) return ((BooleanLiteral) n).toString();
        if (n instanceof InfixExpression) return ((InfixExpression) n).getOperator().toString();
        if (n instanceof PrefixExpression) return ((PrefixExpression) n).getOperator().toString();
        if (n instanceof PostfixExpression) return ((PostfixExpression) n).getOperator().toString();
        if (n instanceof Assignment) return ((Assignment) n).getOperator().toString();
        if (n instanceof TextElement) return n.toString();
        if (n instanceof TagElement) return ((TagElement) n).getTagName();
        if (n instanceof TypeDeclaration) return ((TypeDeclaration) n).isInterface() ? "interface" : "class";
        if (n instanceof ImportDeclaration) return ((ImportDeclaration) n).isOnDemand() ? "on-demand" : "single-type";
        if (n instanceof SwitchCase) return ((SwitchCase) n).isDefault() ? "default" : "";

        return "";
    }

    @Override
    public boolean visit(TagElement e) {
        return true;
    }

    @Override
    public boolean visit(QualifiedName name) {
        return false;
    }

    @Override
    public void postVisit(ASTNode n) {
        popNode();
    }


}
