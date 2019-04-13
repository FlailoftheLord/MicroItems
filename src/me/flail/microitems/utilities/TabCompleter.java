package me.flail.microitems.utilities;

import java.util.ArrayList;

import me.flail.microitems.MicroItems;

public class TabCompleter extends ArrayList<String> {
	private MicroItems plugin;
	/**
	 * UID
	 */
	private static final long serialVersionUID = 10971234812998374L;

	public TabCompleter(MicroItems plugin) {
		this.plugin = plugin;
	}

	public TabCompleter construct() {


		return this;
	}

}
