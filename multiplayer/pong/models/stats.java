/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.models;

/**
 *
 * @author pc
 */
public class stats {
    private User user1;
    private User user2;
    private int score_user1;
    private int score_user2;
    


    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public int getScore_user1() {
        return score_user1;
    }

    public void setScore_user1(int score_user1) {
        this.score_user1 = score_user1;
    }

    public int getScore_user2() {
        return score_user2;
    }

    public void setScore_user2(int score_user2) {
        this.score_user2 = score_user2;
    }
    
    
}
