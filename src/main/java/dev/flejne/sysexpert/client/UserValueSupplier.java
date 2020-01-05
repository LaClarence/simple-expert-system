package dev.flejne.sysexpert.client;

import dev.flejne.sysexpert.domain.Fact.ValueType;

@FunctionalInterface
public interface UserValueSupplier {

	String valueOf(ValueType valuetype, String question);

}
