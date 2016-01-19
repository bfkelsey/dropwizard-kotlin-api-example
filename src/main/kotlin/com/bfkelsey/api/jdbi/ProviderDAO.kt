package com.bfkelsey.api.jdbi

import com.bfkelsey.api.core.Provider
import com.bfkelsey.api.mappers.ProviderMapper
import org.skife.jdbi.v2.sqlobject.*
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(ProviderMapper::class)
interface ProviderDAO {
    @SqlUpdate("INSERT into providers (name, created_at) VALUES (:name, CURRENT_TIMESTAMP)")
    @GetGeneratedKeys
    fun insert(@BindBean provider: Provider) : Int

    @SqlUpdate("update providers set (name, updated_at) = (:name, CURRENT_TIMESTAMP) where id = :id")
    fun update(@Bind("id") id: Int, @BindBean provider: Provider) : Int

    @SqlQuery("select id, name from providers where id = :id")
    fun find(@Bind("id") id: Int): Provider?

    @SqlQuery("select id, name from providers")
    fun findAll(): List<Provider>

    @SqlUpdate("delete from providers where id = :id")
    fun delete(@Bind("id") id: Int) : Int

}
