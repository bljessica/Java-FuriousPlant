package plants;

//南瓜头
public class PumpkinHead extends Plant{

    private int frontTime;
    private int myPlant;

    public PumpkinHead(int h, int l, int x, int y, int timer) {
        super(h, l, x, y, 4000);
        this.frontTime = timer;
        myPlant=0;
    }


    public int getFrontTime() {
        return this.frontTime;
    }

    public void setFrontTime(int frontTime) {
        this.frontTime = frontTime;
    }


	public int getPlant() {
		return myPlant;
	}


	public void setPlant(int plant) {
		this.myPlant = plant;
	}

}