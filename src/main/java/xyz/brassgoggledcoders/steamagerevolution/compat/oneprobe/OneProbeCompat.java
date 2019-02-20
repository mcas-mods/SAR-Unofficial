package xyz.brassgoggledcoders.steamagerevolution.compat.oneprobe;

import java.util.function.Function;

import javax.annotation.Nullable;

import mcjty.theoneprobe.api.ITheOneProbe;

public class OneProbeCompat implements Function<ITheOneProbe, Void> {

	public static ITheOneProbe probe;

	@Nullable
	@Override
	public Void apply(@Nullable ITheOneProbe theOneProbe) {
		probe = theOneProbe;
		return null;
	}
}