Moxy-Parser
===========

Moxy-Parser is a parser written in Java. It is a rethink and redesign of what we think a parser should do. The aim is to provide multiple hooks into the parsing lifecycle and to support multiple grammars and grammar specifications and to allow us to view, optimise and debug such grammars. 


Moxy-Parser is definitly under active development, but there is a lot todo.

If you want to help please get in contact.

Jack

###How we parse

We keep our parsing efficient by sacrificing memory (although not as much as you may think), something which in the old days wasn't possible. Additionally optimizers are provided to further reduce some of the complex overheads of large rule graphs, by removing the human element in grammar design.

When parsing we choose the root of our rule-graph and ask it to consider our sequence of tokens, typically with a given index of that sequence to start from, most often the beginning. If terminal, the rule will assess the relevant tokens from the sequence, it will then make a decision as to whether it has passed or failed. It it has failed we make a note of why, and if it has passed we typically add those tokens to its decision. If functional, the rule will ask the parser to consider its child rules and thus in a depth first traversal we visit all the nodes, the parser will return the decision made by the child rule, and typically the functional rule will add those that decision's tokens to its own, and mark the next index. 

For every character in our stream, we maintain a history of every rule whether passed failed or still evaluating, that started evaluating on that character. This gives several advantages, firstly it allows us to avoid recursion, if a rule is still evaluating, then we can detect it and fail at that point, without getting us into an infinite loop. Secondly if we know a rule has passed or failed before, and either could of been the case, then we don't need to evaluate that rule, we just reuse that decision. This involves saving the tokens it evaluated as well. Thirdly and in my opinion most usefully, this allows us to start debugging what is going on, including being able to replay decisions.
