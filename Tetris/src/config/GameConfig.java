package config;

import java.util.ArrayList;
import java.util.List;

import ui.Layer;

public class GameConfig {

	private int width;
	private int height;
	private int padding;
	private int windowSize;
	private String host;
	private int port;
	private List<Layer> layers = new ArrayList<>();
	//这是玩家２的看板
	private List<Layer> olayers = new ArrayList<>();
	private int refreshOffsetY;
	private int needShadow;
	private int needActBackGround;
	private int border;
	public List<Layer> getOlayers() {
		return olayers;
	}
	public void addLayer(Layer layer){
		
		layers.add(layer);
		
	}
    public void addOLayer(Layer layer){
		
		olayers.add(layer);
		
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public int getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public int getRefreshOffsetY() {
		return refreshOffsetY;
	}

	public void setRefreshOffsetY(int refreshOffsetY) {
		this.refreshOffsetY = refreshOffsetY;
	}

	public int getNeedShadow() {
		return needShadow;
	}

	public void setNeedShadow(int needShadow) {
		this.needShadow = needShadow;
	}

	public int getNeedActBackGround() {
		return needActBackGround;
	}

	public void setNeedActBackGround(int needActBackGround) {
		this.needActBackGround = needActBackGround;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
