package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class StyleFeed {

    class Post implements Comparable<Post> {
        int postId;
        LocalDateTime dateStamp;
        Post(int pid) {
            postId = pid;
            dateStamp = LocalDateTime.now();
        }

        // NOTE THAT I REVERSED POSITION OF THIS AND O SO THAT IT ORDERS THINGS IN DESCENDING ORDER
        @Override
        public int compareTo(Post o) {
            return o.dateStamp.compareTo(this.dateStamp);
        }

        public String toString() {
            return String.format("%d: %s ", this.postId, this.dateStamp.toString());
        }
    }

    // keyed by userId, get a queue of posts orderd by date.
    HashMap<Integer, PriorityQueue<Post>> usersPostingLists;

    StyleFeed() throws InterruptedException {
        usersPostingLists = new HashMap<>();
            createSomeFakeData();
    }

    //
    void createPost(int userId, int postId) {
        if (usersPostingLists.get(userId)  == null) {
            usersPostingLists.put(userId, new PriorityQueue<Post>());
        }
        usersPostingLists.get(userId).add( new Post(postId)   );
    }


    // get the latest 10 entries in
    // all the feeds this user follows
    // plus own feed.
    List<Integer> getFeed(int userId) {
        PriorityQueue<Post> scratch = new PriorityQueue();
        Set<Integer> users = getFollowedUsers(userId);
        users.add(userId);
        for (int u: users) {
            scratch.addAll(usersPostingLists.get(u));
        }
        List<Integer> feed10 = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            System.out.printf(" %s", scratch.peek().toString());
            feed10.add(scratch.poll().postId);
        }
        return feed10;
    }

    // create some fake followed users
    Set<Integer> getFollowedUsers(int userId) {
        return IntStream.iterate(1, n->n+1).limit(4).boxed().collect(Collectors.toSet());
    }

   void createSomeFakeData() throws InterruptedException {
        for (int i: IntStream.iterate(10, n->n+1).limit(10).boxed().collect(Collectors.toList())) {
            createPost(1, i);
            createPost(2, i+10);
            createPost(3, i+20);
            createPost(4, i+30);
            createPost(5, i+40);
            sleep(1000);
       }
   }

}
