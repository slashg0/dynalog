package xyz.slashg.dynalog;

/**
 * A simple interface that acts as a paramaterizable {@link Runnable}.
 *
 * UPDATE: I later learned that a similar interface is already available,
 * so this interface might not be relevant anymore. Will let it exist anyway.
 *
 * Created by SlashG on 4/10/2017.
 */

public interface Callback<T extends Object>
{
	void run(T object);
}
