package com.bfkelsey.api.mappers

import com.bfkelsey.api.core.Provider
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet

class ProviderMapper : ResultSetMapper<Provider> {
    override fun map(index: Int, r: ResultSet, ctx: StatementContext?): Provider {
        return Provider(r.getInt("id"), r.getString("name"))
    }
}
