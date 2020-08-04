import * as fs from 'fs';
import svelte from 'rollup-plugin-svelte';
import scss from 'rollup-plugin-scss';
import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';

export default {
  input: 'src/main/resources/scripts/main.js',
  output: {
	sourcemap: true,
    name: 'bundle',
    file: 'src/main/resources/static/bundle.js',
    format: 'iife'
  },
  plugins: [
    svelte({
      // enable run-time checks when not in production
      dev: true,
      // we'll extract any component CSS out into
      // a separate file - better for performance
      css: css => {
        css.write('src/main/resources/static/bundle.css');
      }
    }),
    scss({
      output: 'src/main/resources/static/global.css'
    }),
    resolve({
      browser: true,
      dedupe: ['svelte']
    }),
    commonjs({
    	include: /node_modules/,
//      namedExports: {'lodash.tonumber': }
    })
  ]
};