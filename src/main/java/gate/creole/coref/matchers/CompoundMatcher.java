/*
 *  CompoundMatcher.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 6 Mar 2012
 *
 *  $Id: CompoundMatcher.java 20042 2017-02-01 06:57:31Z markagreenwood $
 */
package gate.creole.coref.matchers;

import java.util.List;

import gate.creole.ResourceInstantiationException;
import gate.creole.coref.CorefBase;
import gate.creole.coref.Matcher;

/**
 * Abstract class supporting compound matchers. All constituent  sub-matchers 
 * must use the same annotation and antecedent types, otherwise a 
 * {@link ResourceInstantiationException} will be raised.
 */
public abstract class CompoundMatcher extends AbstractMatcher {
  
  protected final Matcher[] subMatchers;
  
  /**
   * Constructs a compound matcher from a list of sub-matchers.
   * @throws ResourceInstantiationException if not all sub-matchers use the same
   * annotation type and antecedent type.
   */
  public CompoundMatcher(List<Matcher> subMatchers) throws ResourceInstantiationException {
    this(subMatchers.toArray(new Matcher[subMatchers.size()]));
  }
  
  /**
   * Constructs a compound matcher from an array of sub-matchers
   * @throws ResourceInstantiationException if not all sub-matchers use the same
   * annotation type and antecedent type. 
   */
  public CompoundMatcher(Matcher[] subMatchers) throws ResourceInstantiationException {
    super(subMatchers[0].getAnnotationType(), 
        subMatchers[0].getAnnotationType());
    this.subMatchers = new Matcher[subMatchers.length];
    System.arraycopy(subMatchers, 0, this.subMatchers, 0, subMatchers.length);
    
    for(Matcher aMatcher : this.subMatchers) {
      if(!aMatcher.getAnnotationType().equals(annotationType)){
        throw new ResourceInstantiationException(
          "Not all submatchers use the same annotation type");
      }
      if(!aMatcher.getAntecedentType().equals(antecedentType)){
        throw new ResourceInstantiationException(
          "Not all submatchers use the same antecedent type");
      }
    }
  }

  @Override
  public void init(CorefBase owner) throws ResourceInstantiationException {
    for(Matcher aMatcher : subMatchers) aMatcher.init(owner);
    
  }
  
  
}
