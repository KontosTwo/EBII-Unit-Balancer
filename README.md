# M2TW EDU Standardizer

### Standardization

Balancing individual units is hard enough, but ensuring some sort
of standardization across dozens, if not hundreds, of units is even worse.
This is especially true for larger M2TW mods which have up to 500 units, each with
their own EDU (export_descr_unit.txt) entry and stats. When you have that many units
whose stats need to be balanced according to a set of rules, and those rules
could change on the fly, modding becomes a pain. 

The Standardizer aims to prevent that pain. You can define your unit stat balancing rules
in a file, and then run this tool to apply those rules to every single unit. This way,
unit stats are balanced in a standardized way free of arbitrary decisions. Best of all,
this requires minimal effort. As a result, a modder can mass-adjust unit stats by editing a single
value in the rules configuration, test them in-game, and then repeat the process
until they are satisfied.

### How to use

##### Command line

```$xslt
java -jar Standardizer.jar <EDU absolute path> <Output absolute path> <Standardizer absolute path> <Attribute Definitions absolute path>
```

##### Attributes

The M2TW engine defines in EDU an entry called "attributes". This is simply a list
of comma-separated values that the engine uses for several features. A modder could add 
any attribute they want, and that attribute will be usable from the battle engine, campaign_script.txt, and 
of course, this tool. Here's an example:

```$xslt
attributes sea_faring, hide_forest, command, hardy, can_withdraw, power_charge, cannot_skirmish, chainmail, longsword
```

An experienced modder may recognize that many of these attributes
are already used by the game engine to handle unit behavior. The last two (chainmail and longsword)
are not used by the game engine - and that's okay. They will not cause the game
to crash, and will serve a purpose later on.

##### Attribute Definitions

The Standardizer needs to associate each usable attribute with a decimal value. Define the attributes' values
in a file in a format like this:

```$xslt
chainmail = 4.0;
longsword = 4.0;
isDisciplined = 1.0;
isShieldWall = 1.0;
maxArmor = 10;
```

Now the Standardizer will associate a value with each attribute during stat calculations.

##### Standardizer

The actual Standardizer is a file that performs a series of basic operations to calculate the units'
stats such as mass, attack, etc from the base set of attributes present on the unit. Using the 
above EDU attributes and attribute definitions, we can derive calculations like thus:

```$xslt
armor = chainmail + helmet + greaves;
mentality = (isDisciplined * 0.4) + (isMotivated * 0.6);
mass = (isDisciplined + isShieldWall) ? 1.2 : (armor / maxArmor);
```
There is a lot to unpack here, so we'll walk through line by line.

1. A variable `armor` is declared that is the sum of the values
`chainmail`, `helmet`, and `greaves`. The unit we're using as an example
has only `chainmail`, however, and that's fine... for us. That simply means the Standardizer,
when calculating stats for this unit, will substitute a default value of 0.0 
for `helmet` and `greaves`. Meanwhile, since `chainmail` is defined on this unit's EDU attributes,
its value will be 4.0 as per the attribute definitions. Thus, the variable
`armor` stores 4.0. 
1. A variable `mentality` is declared that uses fancy logic to say 
"Add 0.4 if the unit is disciplined, and add 0.6 if the unit is motivated". Since 
this unit only has `isDisciplined`, its `mentality` will be 0.4.
1. The `mass` variable is special. Certain variable names are used by the 
Standardizer to write back to EDU. So if the Standardizer at any time defines
the `mass` variable, then whatever value it calculates for the current unit will be written
to that unit's mass. Also notice the ternary operator. It's syntax is 
`
<if value> ? <then value> : <else value>
`
The <if value> evaluates if the value is 0. If not, then the <then value> is returned. 
Otherwise, the <else value> is returned. This is a hacky way of simulating if statements.

Supported syntax include:

1. `+` add
1. `-` subtract
1. `*` multiply
1. `/` divide
1. `?`/`:` ternary
1. `(`/`)` parenthesis
1. declaring and reading variables
1. decimal and integer values


##### Write-back variable names

The mappings from Standardizer variables to EDU stats will be updated
as this tool is worked on. The >---< represents where in the 
EDU entry the value is injected into.

1. mass -> soldier x, x, x, >---<


### Best practices

 - Define lots of intermediary variables. This way, you can reuse calculations
 for other stats