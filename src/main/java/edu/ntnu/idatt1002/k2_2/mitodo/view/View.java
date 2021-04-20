package edu.ntnu.idatt1002.k2_2.mitodo.view;

public abstract class View extends Component
{
    public abstract String getMainMenuTitle();
    public abstract boolean equals(View view);
    public void update(){}
}