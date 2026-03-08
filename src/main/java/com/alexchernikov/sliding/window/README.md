## Problem definition:
Given a string s, find the length of the longest substring t that contains at most 2 distinct characters.

Example:
- Input: "eceba"
- Output: 3
- Explanation: t is "ece" which length is 3.

### Solution:
It is classic sliding window problem where window size is dynamic.
Key idea: we maintain a sliding window [left, right] and track how many distinct characters are inside it.
Steps:
- Expand the window by moving right.
- Track character frequencies in a map.
- If the map contains more than 2 distinct characters, shrink the window from the left
- Keep updating the maximum length.

### Step 1
- e c e b a
- ^
- L,R
- Window: "e"

Map:
- {e:1}

- distinct characters = 1
- Length = 1
- Max = 1

### Step 2
- Move right ->
- e c e b a
- ^ ^
- L R
- Window: "ec"

Map:
- {e:1, c:1}

- distinct characters = 2
- Length = 2
- Max = 2

### Step 3
- Move right ->
- e c e b a
- ^   ^
- L   R
- Window: "ece"

Map:
- {e:2, c:1}

- distinct characters = 2
- Length = 3
- Max = 3

### Step 4
- Move right ->
- e c e b a
- ^     ^
- L     R
- Window: "eceb"

Map:
- {e:2, c:1, b:1}

- distinct characters = 3 X
- Rule violated - shrink window

### Shrink 1
- Move left ->
- e c e b a
-   ^   ^
-   L   R
- Window: "ceb"

Map:
- {e:1, c:1, b:1}

- Still distinct characters = 3 X
- Rule violated - shrink window again

### Shrink 2
- Move left ->
- e c e b a
-     ^ ^
-     L R
- Window: "eb"

Map:
- {e:1, b:1}

- distinct characters = 2
- Length = 2
- Max still = 3

### Step 5
- Move right ->
- e c e b a
-     ^   ^
-     L   R
- Window: "eba"

Map:
- {e:1, b:1, a:1}

- distinct characters = 3 X
- Rule violated - shrink window again

### Shrink 1
- Move left ->
- e c e b a
-       ^ ^
-       L R
- Window: "ba"

Map:
- {b:1, a:1}

- distinct characters = 2
- Length = 2

### Final result:
Longest window we saw:
- ece
- Length: 3