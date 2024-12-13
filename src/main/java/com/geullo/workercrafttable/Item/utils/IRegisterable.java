package com.geullo.workercrafttable.Item.utils;

public interface IRegisterable<T> {
    void registerItemModel();
    void updateRegistryAndLocalizedName(String name);
}
