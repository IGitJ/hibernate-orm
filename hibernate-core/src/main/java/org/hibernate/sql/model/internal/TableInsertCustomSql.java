/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html.
 */
package org.hibernate.sql.model.internal;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.ast.AbstractTableInsert;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.CustomSqlMutation;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.jdbc.JdbcInsertMutation;

/**
 * Insertion defined using custom sql-insert
 *
 * @see org.hibernate.annotations.SQLInsert
 *
 * @author Steve Ebersole
 */
public class TableInsertCustomSql extends AbstractTableInsert implements CustomSqlMutation<JdbcInsertMutation> {

	public TableInsertCustomSql(
			MutatingTableReference mutatingTable,
			MutationTarget<?> mutationTarget,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, parameters, valueBindings );
	}

	@Override
	public String getCustomSql() {
		return getMutatingTable().getTableMapping().getInsertDetails().getCustomSql();
	}

	@Override
	public boolean isCallable() {
		return getMutatingTable().getTableMapping().getInsertDetails().isCallable();
	}

	@Override
	public List<ColumnReference> getReturningColumns() {
		return Collections.emptyList();
	}

	@Override
	public void forEachReturningColumn(BiConsumer<Integer, ColumnReference> consumer) {
		// nothing to do
	}

	@Override
	public void accept(SqlAstWalker walker) {
		walker.visitCustomTableInsert( this );
	}
}
